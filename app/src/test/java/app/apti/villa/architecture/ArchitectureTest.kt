package app.apti.villa.architecture

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.withPackage
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.Test

class ArchitectureTest {

    @Test
    fun `domain layer does not depend on android, room, okhttp, or hilt`() {
        Konsist
            .scopeFromProject()
            .files
            .withPackage("app.apti.villa.domain..")
            .assertTrue { file ->
                val imports = file.imports.map { it.name }
                imports.none { importName ->
                    importName.startsWith("android.") ||
                    importName.startsWith("androidx.") ||
                    importName.startsWith("dagger.hilt.") ||
                    importName.startsWith("okhttp3.")
                }
            }
    }

    @Test
    fun `network classes are not imported outside ai or core common`() {
        Konsist
            .scopeFromProject()
            .files
            .assertTrue { file ->
                val pkg = file.packagee?.name ?: ""
                val isAi = pkg.startsWith("app.apti.villa.ai")
                val isCommon = pkg == "app.apti.villa.core.common"

                if (isAi || isCommon) {
                    true
                } else {
                    val imports = file.imports.map { it.name }
                    imports.none { importName ->
                        importName.startsWith("okhttp3.") ||
                        importName.startsWith("java.net.") ||
                        importName.startsWith("javax.net.")
                    }
                }
            }
    }

    @Test
    fun `data local does not depend on feature or ai`() {
        Konsist
            .scopeFromProject()
            .files
            .withPackage("app.apti.villa.data.local..")
            .assertTrue { file ->
                val imports = file.imports.map { it.name }
                imports.none { importName ->
                    importName.startsWith("app.apti.villa.feature.") ||
                    importName.startsWith("app.apti.villa.ai.") ||
                    importName.startsWith("okhttp3.")
                }
            }
    }

    @Test
    fun `feature layer does not depend directly on room entities`() {
        Konsist
            .scopeFromProject()
            .files
            .withPackage("app.apti.villa.feature..")
            .assertTrue { file ->
                val imports = file.imports.map { it.name }
                imports.none { importName ->
                    importName.startsWith("app.apti.villa.data.local.entity.")
                }
            }
    }
}
