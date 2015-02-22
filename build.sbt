import android.Keys._

javacOptions ++= Seq("-source", "1.7", "-target", "1.7")

android.Plugin.androidBuild

name := "quit"

scalaVersion := "2.11.5"

proguardCache in Android ++= Seq(
  ProguardCache("org.scaloid") % "org.scaloid"
)

proguardOptions in Android ++= Seq(
  "-dontobfuscate",
  "-dontoptimize",
  "-keepattributes Signature",
  "-printseeds target/seeds.txt",
  "-printusage target/usage.txt",
  "-keep class org.ocpsoft.prettytime.i18n.**",
  "-keep class com.github.nscala_time.time.**",
  "-dontwarn scala.collection.**" // required from Scala 2.11.4
)

apkbuildExcludes in Android ++= Seq(
  "META-INF/LICENSE.txt",
  "META-INF/NOTICE.txt"
)

libraryDependencies ++= Seq(
  "org.scaloid"            %% "scaloid"            % "3.6.1-10" withSources() withJavadoc(),
  "com.lihaoyi"            %% "upickle"            % "0.2.6",
  "com.github.nscala-time" %% "nscala-time"        % "1.8.0",
  "com.loopj.android"      %  "android-async-http" % "1.4.5",
  "org.ocpsoft.prettytime" %  "prettytime"         % "3.2.7.Final"
)

scalacOptions in Compile += "-feature"

run <<= run in Android

install <<= install in Android
