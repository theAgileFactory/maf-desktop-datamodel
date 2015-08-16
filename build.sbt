name := "maf-desktop-datamodel"

version := "dist"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.6"

//Uncomment to add parameters to javac in SBT
//javacOptions ++= Seq("-Xlint:deprecation")

// Prevent ScalaDoc generation and packaging
publishArtifact in (Compile, packageDoc) := false

sources in (Compile,doc) := Seq.empty

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
//routesGenerator := InjectedRoutesGenerator

//Eclipse plugin directives
EclipseKeys.preTasks := Seq(compile in Compile)
EclipseKeys.projectFlavor := EclipseProjectFlavor.Java
EclipseKeys.createSrc := EclipseCreateSrc.ValueSet(EclipseCreateSrc.ManagedClasses, EclipseCreateSrc.ManagedResources)

