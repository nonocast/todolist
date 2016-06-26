master

v0.1.5 2016/6/26
1. 通过@Value(project.version)获取application.properties
2. build.gradle中通过Properties和PropertiesFile获取application.perperties中版本号

v0.1.4 2016/6/26
1. 增加Spring Boot， Spring Security和Freemarker支持。
2. 增加devtools支持
  1) gradle build --continuous
  2) gradle bootRun
2. 导入到IntelliJ IDEA
  1) create Spring Boot project with SB V1.3 and add "Devtools" (1*) to dependencies
  2) invoke Help->Find Action... and type "Registry", in the dialog search for "automake" and enable the entry "compiler.automake.allow.when.app.running", close dialog
  3) enable background compilation in Settings->Build, Execution, Deployment->Compiler "Make project automatically"

nonocast 创建于 2016/6/25
