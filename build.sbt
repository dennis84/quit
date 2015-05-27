android.Plugin.androidBuild

name := "quit"

javacOptions ++= Seq("-source", "1.7", "-target", "1.7")

scalaVersion := "2.11.6"
scalacOptions in Compile += "-feature"
scalaSource in Test := baseDirectory.value / "test"

run <<= run in Android

resolvers += "jitpack" at "https://jitpack.io"

libraryDependencies ++= Seq(
  "com.android.support"    %  "support-v4"           % "22.1.0",
  "com.android.support"    %  "appcompat-v7"         % "22.1.0",
  "com.squareup"           %  "otto"                 % "1.3.7",
  "org.scalatest"          %% "scalatest"            % "2.2.4" % "test",
  "com.github.nscala-time" %% "nscala-time"          % "2.0.0",
  "com.melnykov"           %  "floatingactionbutton" % "1.3.0",
  "com.github.lecho"       %  "hellocharts-library"  % "1.5.4"
)

proguardScala in Android := true

proguardOptions in Android ++= Seq(
  "-ignorewarnings",
  "-keepattributes *Annotation*",
  """-keepclassmembers class ** {
    @com.squareup.otto.Subscribe public *;
    @com.squareup.otto.Produce public *;
  }"""
)

apkbuildExcludes in Android ++= Seq(
  "META-INF/LICENSE.txt",
  "META-INF/NOTICE.txt"
)
