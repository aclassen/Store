Cache-KMP
===================
## Cache KMP port

Store depends on a subset of Guava, we have extracted these parts into a shaded Cache artifact.  
Feel free to use Cache anytime you need an in memory cache implementation optimized for Android.


To use, first build a cache instance.

```java
val cache = cacheBuilder<String, String> {
        maximumSize(20)
        expireAfterAccess(5.minutes)
        }
```


Please refer to Guava's Cache documentation for additional features/configurations
https://github.com/google/guava/wiki/CachesExplained

```groovy
	implementation "org.burnoutcrew.store:cache-kmp:${store_version}"
```
Please refer to the [Store Readme](https://github.com/dropbox/Store/blob/main/README.md#latest-version) for the latest version.

```
Copyright (c) 2017 The New York Times Company

Copyright (c) 2010 The Guava Authors

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this library except in 
compliance with the License. You may obtain a copy of the License at

www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an 
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific 
language governing permissions and limitations under the License.
```