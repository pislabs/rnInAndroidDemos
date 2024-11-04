## 集成已有 android 系统

### 步骤

#### 1. 创建项目结构

创建文件夹 android, 并将已有安卓项目已入此文件夹

#### 2. 安装 npm 依赖

在根目录下执行如下命令，以创建标准 package.json 文件，并安装 npm 包

```bash
curl -O https://raw.githubusercontent.com/react-native-community/template/refs/heads/0.75-stable/template/package.json

yarn
```

#### 3. 添加 react native 到 android 项目

将 android 项目复制到项目根目录下，并修改根文件夹名称为 android

编辑 android 项目下的 settings.gralde 文件，新增如下行

```java

// Configures the React Native Gradle Settings plugin used for autolinking
pluginManagement { includeBuild("../node_modules/@react-native/gradle-plugin") }
plugins { id("com.facebook.react.settings") }

extensions.configure<com.facebook.react.ReactSettingsExtension> { autolinkLibrariesFromCommand() }
// If using .gradle files:
extensions.configure(com.facebook.react.ReactSettingsExtension){ ex -> ex.autolinkLibrariesFromCommand() }

includeBuild("../node_modules/@react-native/gradle-plugin")

// Include your existing Gradle modules here.
// include(":app")

```

在等不 build.gralde.kts 中包含如下代码

```java
buildscript {
    dependencies {
        classpath(libs.gradle)
        classpath(libs.react.native.gradle.plugin)
    }
}
```

## 参考

- [Get Started Without a Framework](https://reactnative.dev/docs/getting-started-without-a-framework)
- [Integration with Existing Apps](https://reactnative.dev/docs/integration-with-existing-apps)
