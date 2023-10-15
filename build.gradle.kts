import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * this is a top-level gradle buildscript.
 * Project-wide dependencies, such as mindustry, arc and kotlin, should be declared here.
 * Read the comments for more info.
 */

plugins {
	// when modifying the version of this plugin, you must also modify the version in other buildscripts:
	// all modules must share the same version or the compilation will fail
	kotlin("jvm") version "1.8.0"
}

allprojects {
	// this one applies to every script, its version is inherited from the kotlin plugin version.
	apply(plugin = "org.jetbrains.kotlin.jvm")

	repositories {
		// Repositories to download dependencies from.
		// Do not remove anything other than mavenLocal unless you know what you're doing
		mavenCentral()
		mavenLocal()
		// Needed, as jitpack is now practically incapable of compiling mindustry
		// due to it's repository size.
		// However, jitpack is still kept for arc and for any custom libraries.
		maven("https://raw.githubusercontent.com/Zelaux/MindustryRepo/master/repository")
		maven("https://jitpack.io")
	}

	dependencies {
		// be cautious: dependencies applied here are applied to every submodule
		// never insert local project dependencies (e.g. implementation(project(":another-module"))).

		// in addition, never declare mindustry, arc and other mods (except libraries) as implementation deps!
		// mindustry itself will refuse importing the mod if it sees that it contains the mindustry dependency.
		
		// arc dependency
		compileOnly("com.github.Anuken.Arc", "arc-core", "v146")
		// mindustry dependency.
		// versions lower than v145.1 cannot be used as MindustryRepo started on that version
		compileOnly("com.github.Anuken", "Mindustry", "v146")
		//example of a library dependency. if you don't need it, remove this line.
		//(note: this is not a mod, it's a library for mindustry mods, thus it should be added as an implementation dependency.)
		implementation("com.github.mnemotechnician", "mkui", "v1.2.1")
	}

	tasks.withType<JavaCompile> {
		sourceCompatibility = "1.8"
		targetCompatibility = "1.8"
	}

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			// target java version - 8. do not increase unless you really need to
			// this will not change a lot but will break mobile compatibility.
			jvmTarget = "1.8"
			// kotlin compiler argument
			freeCompilerArgs += arrayOf(
				// use the experimental kotlin compiler - x2 speed
				"-Xuse-k2",
				// enable context receivers
				// note: context receivers seem to be kinda broken in the k2 compiler.
				"-Xcontext-receivers"
			)
		}
	}
}
