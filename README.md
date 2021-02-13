<p align="center">
    <img src="https://cdn.jsdelivr.net/gh/GceCold/WebImages/OrangeAPI/OrangeAPI.png" width="320px">
</p>

## OrangeAPI

[![](https://img.shields.io/github/license/GceCold/OrangeAPI?style=flat-square)](https://github.com/GceCold/OrangeAPI)
[![](https://img.shields.io/github/workflow/status/GceCold/OrangeAPI/Java%20CI%20with%20Maven?style=flat-square)](https://img.shields.io/github/workflow/status/GceCold/OrangeAPI/Java%20CI%20with%20Maven?style=flat-square)

The OrangeAPI provides some web API framework to easy develop.

This project originated from [VexMusic](https://github.com/GceCold/VexMusic).

Author can't continue to develop because of the national college entrance examination.But `Netease` API you can easy use.

## Inclusion

1. Netease Music
2. Bilibili (Beta)

## Requirement

JDK 8 is required.

## Usage

### Netease

OrangeAPI has developed 4 parts of Netease Music API.

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

You can refer to [Binaryify/NeteaseCloudMusicApi](https://github.com/Binaryify/NeteaseCloudMusicApi "Binaryify/NeteaseCloudMusicApi") to develop new api.it's easy

Such as [NeteasePlayListAPI#playlistDetail](https://github.com/GceCold/OrangeAPI/blob/master/src/main/java/ltd/icecold/orange/netease/api/NeteasePlayListAPI.java "playlistDetail")

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

### Bilibili

#### VideoDownload

```java
//File format is "FLV"
new BilibiliVideoAPI().getVideoUrlV1("BV1uv411z7MB").download(new File("1.flv"), 10);
```
`BilibiliDownloaderThread#getCompleteRate` can get downloading complete rate

#### Login

It's still under development

## Maven Dependency

Add the following Maven dependency to your own project pom.xml:

```
<dependency>
    <groupId>ltd.icecold</groupId>
    <artifactId>OrangeAPI</artifactId>
    <version>1.0.1</version>
</dependency>
```

### Gradle

```
compile group: 'ltd.icecold', name: 'OrangeAPI', version: '1.0.1'
```


## Thanks

[Binaryify/NeteaseCloudMusicApi](https://github.com/Binaryify/NeteaseCloudMusicApi "Binaryify/NeteaseCloudMusicApi")

[SocialSisterYi/bilibili-API-collect](https://github.com/SocialSisterYi/bilibili-API-collect "SocialSisterYi/bilibili-API-collect")

