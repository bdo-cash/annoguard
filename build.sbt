name := baseDirectory.value.getName

organization := "hobby.wei.c.anno"

version := "1.0.3"

scalaVersion := "2.11.7"

//libraryProject := true

exportJars := true

// 可以去掉与 Scala 版本的关联
crossPaths := false

autoScalaLibrary := false

// 解决生成文档报错导致 jitpack.io 出错的问题。
publishArtifact in packageDoc := false

//proguardVersion := "5.2.1" // 必须高于 5.1，见 https://github.com/scala-android/sbt-android。

// sourceDirectories 包括了 javaSource 和 scalaSource，但不包含 sourceDirectory。
//sourceDirectories in Compile += baseDirectory.value / "src"
//sourceDirectories in Test := Seq(baseDirectory.value / "test/src")

// Java Code 必须用这种方式
javaSource in Compile := baseDirectory.value / "src"
javaSource in Test := baseDirectory.value / "test/src"

//scalaSource := ???

// Default unmanaged resource directory, used for user-defined resources.
//resourceDirectory := ???
// List of all resource directories, both managed and unmanaged.
resourceDirectories in Compile += baseDirectory.value / "impl"

// Filter for including sources and resources files from default directories.
//includeFilter := "*.java" | "*.scala" | "*.pro"

libraryDependencies += "com.google.code.gson" % "gson" % "2.8.0"

packageConfiguration in Compile in packageBin := {
  val oldPkgConf = (packageConfiguration in Compile in packageBin).value
  val impl = "impl"

  // 一个*表示在所有直接子路径中查找，两个**表示在所有子路径（包括直接和间接）中查找。
  val sources = ((baseDirectory.value / impl) * "*.pro").get.map(f => (f, impl + Path.sep + f.getName))

  streams.value.log.info("[packageConfAddSource] done. -------------------->")
  streams.value.log.info("annoguard sources: " + sources.mkString("\n(", ",\n", ")\n"))
  val newPkgConf = new Package.Configuration(oldPkgConf.sources ++ sources, oldPkgConf.jar, oldPkgConf.options)

  newPkgConf
}

// TODO: 对 .pro 混淆配置文件进行解包（前面打包进了 jar 文件），针对使用这个库的项目。
// 参见：https://stackoverflow.com/questions/18610040/how-to-finding-a-zip-dependency-path

//classpathTypes += "zip" // 如果是zip文件的话，我们这里不是。

// TODO: 待续。。。
//val bundleFile = taskKey[File]("bundle's path")
//bundleFile := {
//  val report: UpdateReport = update.value
//  val filter = artifactFilter(name = "bar-bundle", extension = "zip")
//  val all: Seq[File] = report.matching(filter)
//  all.headOption getOrElse error("Could not find bar-bundle")
//}

// 这种"~="写法的代码段里面无法引用 xxxKey.value，只有像上面":="的写法可以。
//packageBin ~= (bin => { newBin })

// 这种"<<="写法被弃用
//lazy val pkgTask = taskKey[Unit]("") <<= (baseDirectory,
//  target,
//  fullClasspath in Compile,
//  packageBin in Compile,
//  resources in Compile,
//  streams) map { // 这个 map 有个神奇的地方在于将前面的 xxxKey 参数进行了 .value（有限制）求值后作为后面函数的入参。
//  (baseDir, targetDir, cp, jar, res, s) => {
//    s.log.info("[打包] 创建目录impl ...")
//  }
//}
