gradle就是一个Java程序，通过JVM去运行。

初始化一个Gradle项目，gradle init --type java-application --dsl kotlin

task任务： 可以看到插件贡献的任务
./gradlew tasks
./gradlew :app:tasks 
./gradlew :app:tasks --all


有很多内置任务如Exec：
tasks.register<Exec>("MyExecTask") {
    commandLine("java", "-version")
}

表示当前项目的Java编译版本，但是当依赖库不兼容时，会报错：
//       > Dependency resolution is looking for a library compatible with JVM runtime version 11, but 'project :utilities' is only compatible with JVM runtime version 21 or newer.

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}


对于如HashMap这种的库，是不会参与打包的。JRE已经包含了这些标准库。


jdeps -s /Users/yankang/Project/mix-project/cli/gradle-project/app/build/libs/app.jar
使用jdeps分析jar包当中的依赖，java.base包含了如java.lang等等


module的含义是group+name，group是组织，name是模块名。用来定位某个依赖库。
[libraries]
guava = { module = "com.google.guava:guava", version.ref = "guava" }
junit-jupiter = { module = "org.junit.jupiter:junit-jupiter", version.ref = "junit-jupiter" }


可以查看项目的编译时依赖和运行时依赖：这里的依赖表示的是项目本身的依赖，向jdk是不会在这里的
编译时依赖：只参与编译，不参与打包
运行时依赖：会打包进去
./gradlew :app:dependencies


compileClasspath - Compile classpath for source set 'main'.
+--- org.apache.commons:commons-text -> 1.12.0
|    \--- org.apache.commons:commons-lang3:3.14.0
+--- project :utilities
|    \--- project :list
+--- org.apache.httpcomponents.client5:httpclient5:5.4.1
|    +--- org.apache.httpcomponents.core5:httpcore5:5.3.1
|    +--- org.apache.httpcomponents.core5:httpcore5-h2:5.3.1
|    |    \--- org.apache.httpcomponents.core5:httpcore5:5.3.1
|    \--- org.slf4j:slf4j-api:1.7.36
\--- org.apache.commons:commons-text:1.12.0 (c)


传递依赖：a依赖b，b依赖c，a对c就是传递依赖

传递依赖可能会有版本兼容问题



gradle.properties: 这样执行任务的时候，会把任务列表都打印出来
org.gradle.console=verbose



UP-TO-DATE：任务的输入没有变化，复用之前的结果。


