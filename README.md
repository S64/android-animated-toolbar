# AnimatedToolbar

<a href='https://play.google.com/store/apps/details?id=jp.s64.android.animatedtoolbar.example&pcampaignid=MKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png' height="60" /></a>

An example app of S64/android-animated-toolbar.

This is contains below components:

- AnimatedToolbar
- AnimatedToolbarActivity
- AnimatedToolbarHelper
- ActionBarWrapper

## Usages

Add following lines to your buildscripts.

```groovy
buildscript {
    ext {
        animated_toolbar_version = '0.0.1'
    }
}
```

```groovy
repositories {
    maven {
        url 'http://dl.bintray.com/s64/maven'
    }
}

dependencies {
    compile("jp.s64.android:animatedtoolbar:${animated_toolbar_version}") {
        exclude group: 'com.android.support', module: 'appcompat-v7'
    }
}
```

## License

```
Copyright 2017 Shuma Yoshioka

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
