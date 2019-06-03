name := "akka-model-gen"
organization := "com.github.apuex"
version := "1.0.0"
scalaVersion := "2.12.8"

publishTo := Some(Resolver.file("file",  new File(Path.userHome.absolutePath+"/.m2/repository")))

libraryDependencies ++= {
  val jodaTimeVersion = "2.10.1"
  Seq(
    "com.github.apuex.springbootsolution" %% "runtime"     % "1.0.8",
    "org.scala-lang.modules" %% "scala-xml"                % "1.2.0",
    "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2",
    "org.scala-lang.modules" %% "scala-swing"              % "2.1.1",
    "joda-time"              %  "joda-time"                % jodaTimeVersion,
    "org.scalatest"          %% "scalatest"                % "3.0.7"           % "test"
  )
}

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", "io.netty.versions.properties") => MergeStrategy.rename
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}

mainClass in assembly := Some("com.github.apuex.akka.gen.Main")
assemblyJarName in assembly := "akka-model-gen.jar"


