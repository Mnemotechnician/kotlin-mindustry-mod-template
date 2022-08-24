import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * this is a top-level gradle buildscript.
 * Project-wide dependencies, such as mindustry, arc and kotlin, should be declared here.
 * Read the comments for more info.
 */

plugins {
	// when modifying the version of this plugin, you must also modify the version in other buildscripts:
	// all modules must share the same version or the compilation will fail
	kotlin("jvm") version "1.7.20-Beta"
}

allprojects {
	// this one applies to every script, its version is inherited from the kotlin plugin version.
	apply(plugin = "org.jetbrains.kotlin.jvm")

	repositories {
		// Repositories to download dependencies from.
		// Do not remove anything other than mavenLocal unless you knoa what you're doing
		mavenCentral()
		mavenLocal()
		maven("https://jitpack.io")
	}

	dependencies {
		// be cautious: dependencies applied here are applied to every submodule
		// never insert local project dependencies (e.g. implementation(project(":another-module"))).

		// in addiction, never declare mindustry, arc and other mods (not libraries) as implementation deps!
		// this will greatly increase the size of your mod and will most likely break something at runtime!
		
		implementation(kotlin("stdlib-jdk8"))
		// arc dependency
		compileOnly("com.github.Anuken.Arc:arc-core:v137")
		// mindustry dependency.
		// jitpack refuses to compile mindustry due to its repository size, thus we're using a mirror
		// 74a0321db8 is the hash of a commit, you can replace it with a different one or a specific version (e.g. v137)
		compileOnly("com.github.Anuken:MindustryJitpack:74a0321db8")
		//example of a library dependency. if you don't need it, remove this line.
		//(note: this is not a mod, it's a library for mindustry mods, thus it should be added as an implementation dependency.
		implementation("com.github.mnemotechnician:mkui:v1.1")
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
