import com.github.os72.protocjar.Protoc
import scala.reflect.io.Directory
name := "my-proto"

organization := "com.example"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.7"

crossPaths := false

libraryDependencies ++= Seq(
  "com.google.protobuf" % "protobuf-java" % "3.2.0"
)

lazy val proto = taskKey[Unit]("A protocol buffer compile task")

proto:= {
  val srcLocation = baseDirectory.value / "src/main/java"
  val protoFilesDirectory = baseDirectory.value / "src/main/resources/protos-repo/"
  val destinationLocation = srcLocation
  // remove the existing generated files
  Directory(destinationLocation).createDirectory(force = true)
  Directory(destinationLocation/".gitkeep").createFile(failIfExists = false)
  // find all the protoFiles in protoFilesDirectory
  val protoFiles = protoFilesDirectory.listFiles()
    .filter(f => !f.isDirectory && f.getName.toLowerCase.endsWith(".proto"))
    .map(f => f.getAbsolutePath);
  var args = List(s"-I=$protoFilesDirectory", s"--java_out=$destinationLocation");
  args ++= protoFiles
  println("ARGUMENTS : " + args.mkString(" , "))
  Protoc.runProtoc(args.toArray)
}

compile in Compile <<= (compile in Compile).dependsOn(proto)

publishArtifact in (Compile, packageDoc) := false


jacoco.settings

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

publishTo := {
  val hostRepo = "https://artifactory-host/artifactory/"
  if (isSnapshot.value)
    Some("Artifactory Realm" at hostRepo + "snapshots")
  else
    Some("Artifactory Realm"  at hostRepo + "release")
}
