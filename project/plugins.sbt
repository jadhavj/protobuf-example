logLevel := Level.Warn

// Jar dependencies for sbt build script
libraryDependencies += "com.github.os72" % "protoc-jar" % "3.2.0"

addSbtPlugin("de.johoop" % "jacoco4sbt" % "2.1.6")

addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "3.0.0")

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.13.0")