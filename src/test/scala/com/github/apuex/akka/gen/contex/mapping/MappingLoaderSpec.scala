package com.github.apuex.akka.gen.contex.mapping

import org.scalatest.{FlatSpec, Matchers}

import com.github.apuex.akka.gen.contex.mapping.MappingLoader._

class MappingLoaderSpec extends FlatSpec with Matchers {
  "A MappingLoader" should "load mapping project settings from xml" in {
    val m = MappingLoader("src/test/resources/com/github/apuex/akka/gen/contex/mapping/mappings.xml")
    import m._

    modelName should be("bc1_to_bc2")
    modelPackage should be("com.apuex.sales.mapping")
    modelVersion should be("1.0.0")
    outputDir should be(s"${System.getProperty("output.dir", "target/generated")}")
    rootProjectName should be("bc1-to-bc2")
    rootProjectDir should be(s"${outputDir}/bc1-to-bc2")
    mappingProjectName should be("bc1-to-bc2-mapping")
    mappingProjectDir should be(s"${outputDir}/bc1-to-bc2/bc1-to-bc2-mapping")
    srcDir should be(s"${mappingProjectDir}/src/main/scala/com/apuex/sales/mapping/bc1ToBc2")
    srcPackage should be("com.apuex.sales.mapping.bc1ToBc2")
    symboConverter should be(if ("microsoft" == s"${System.getProperty("symbol.naming", "microsoft")}")
      "new IdentityConverter()" else "new CamelToCConverter()")
    docsDir should be(s"${outputDir}/bc1-to-bc2/docs")
    classNamePostfix should be(s"Mapping")
    hyphen should be(if ("microsoft" == s"${System.getProperty("symbol.naming", "microsoft")}") "" else "-")

    importPackages(xml) should be(
      s"""
         |import com.apuex.sales.message._
         |import akka.persistence.query._
         |import javax.inject._
       """.stripMargin.trim)

    val expectedImports =
      s"""
         |import com.google.protobuf.timestamp.Timestamp
         |import com.apuex.sales.message._
         |import akka.persistence.query._
         |import javax.inject._
     """.stripMargin
          .trim
    xml.child.filter(x => x.label == "service")
      .foreach(x => {
        s"""
           |${importPackages(x)}
           |${importPackages(xml)}
     """.stripMargin
          .trim should be(expectedImports)
      })

  }
}