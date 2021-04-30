<p align="center">
    <img src="https://cdn.jsdelivr.net/gh/GceCold/WebImages/OrangeAPI/OrangeAPI.png" width="320px">
</p>

## OrangeAPI

[![](https://img.shields.io/github/license/GceCold/OrangeAPI?style=flat-square)](https://github.com/GceCold/OrangeAPI)
[![](https://img.shields.io/github/workflow/status/GceCold/OrangeAPI/Java%20CI%20with%20Maven?style=flat-square)](https://img.shields.io/github/workflow/status/GceCold/OrangeAPI/Java%20CI%20with%20Maven?style=flat-square)

The OrangeAPI provides a Web-based API framework for developers wishing to develop applications more easily.

This project originates from [VexMusic](https://github.com/GceCold/VexMusic).

The author (IceCold) can't continue to develop new APIs and fix issues because of the upcoming National Matriculation Entrance Test. But you can easily use the `Netease` API which is already quite complete.

## Capabilities

By using OrangeAPI, you can access the APIs of Netease Music and Bilibili more easily. APIs related to Bilibili are under development.

1. Netease Music
2. Bilibili (Beta)

## Requirement

JDK 8 or above is required.

## Usage

### Netease

4 parts of Netease Music API have been encapsulated by the Orange API framework and can be used directly.

JavaDoc：[https://gcecold.github.io/OrangeAPI/javadoc/](https://gcecold.github.io/OrangeAPI/javadoc/ "JavaDoc")

#### User Login

```java
NeteaseUserAPI neteaseUserAPI = new NeteaseUserAPI();
//md5 password
//email
neteaseUserAPI.login("xxxxx@126.com","7b7bc2512ee1fedcd76bdc68926d4f7b");
//phone
neteaseUserAPI.loginPhone("13333333333","7b7bc2512ee1fedcd76bdc68926d4f7b");
```

#### Custom

You can refer to [Binaryify/NeteaseCloudMusicApi](https://github.com/Binaryify/NeteaseCloudMusicApi "Binaryify/NeteaseCloudMusicApi") to develop your own new API.

Here is an example. [NeteasePlayListAPI#playlistDetail](https://github.com/GceCold/OrangeAPI/blob/master/src/main/java/ltd/icecold/orange/netease/api/NeteasePlayListAPI.java "playlistDetail")

```java
public static NeteaseResponseBody playlistDetail(String id,String s,Map<String,String> cookie){
    Map<String, String> data = new HashMap<>();
    data.put("id", id);
    data.put("n", "100000");
    data.put("s", s);
    NeteaseRequestOptions requestOptions = new NeteaseRequestOptions("https://music.163.com/api/v6/playlist/detail", NeteaseCrypto.CryptoType.LINUXAPI, cookie, Request.UserAgentType.PC);
    return NeteaseRequest.postRequest(requestOptions, data);
}
```

#### Demo

A simple music bot：[https://github.com/GceCold/MusicBot](https://github.com/GceCold/MusicBot)

### Bilibili

#### VideoDownload

```java
//File format must be "FLV"
new BilibiliVideoAPI().getVideoUrlV1("BV1uv411z7MB").download(new File("1.flv"), 10);
```
By calling the method `BilibiliDownloaderThread#getCompleteRate`, you can get the completion progress of the download.

#### Login

It's still under development.

## Maven Dependency

Add the following Maven dependency to the pom.xml file of your own project:

```
<dependency>
    <groupId>ltd.icecold</groupId>
    <artifactId>OrangeAPI</artifactId>
    <version>1.0.1</version>
</dependency>
```

### Gradle

To use Orange API via Gradle, add the following code to your build.gradle file.

```
implementation group: 'ltd.icecold', name: 'OrangeAPI', version: '1.0.1'
```


## Thanks

[Binaryify/NeteaseCloudMusicApi](https://github.com/Binaryify/NeteaseCloudMusicApi "Binaryify/NeteaseCloudMusicApi")

[SocialSisterYi/bilibili-API-collect](https://github.com/SocialSisterYi/bilibili-API-collect "SocialSisterYi/bilibili-API-collect")

