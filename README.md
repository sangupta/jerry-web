jerry-web
==========

Common Java functionality for building web applications.

`jerry-web` is a module library for the uber `jerry` library project. This module provides helper functionality 
when writing Java web applications - such as common filters, request wrappers, custom tags, and utility classes
to work with java.servlet cookies, request/response and more.

For more information on the project, refer to https://github.com/sangupta/jerry project.

Release Notes
-------------

**Current Development**

**0.6.1**

**0.5.0**

* Added GSON based jersey JSON provider
* Added XStream based jersey XML provider
* Added servlet filters
 * LeverageBrowserCacheFilter
 * RequestCapturingFilter
* Added JSP custom tags
 * UserName
 * TimeAgo
 * SignedIn
 * RadioButton
 * JavascriptInclude
 * HexFormat
 * EncodeUriComponent
 * DateFormat
 * CheckBox
 * Base64
 * Base62
 * Anonymous
 * AllJavascript
* Added utility classes
 * RequestUtils
 * ResponseUtils
 * LogUtils
 * CookieUtils
* Added convenience classes
 * UserAwareHttpServletRequestWrapper
 * ByteArrayServletOutputStream
 * HttpServletResponseWrapper
 * ModifiedServletResponse

Downloads
---------

The library can be downloaded from Maven Central using:

```xml
<dependency>
    <groupId>com.sangupta</groupId>
    <artifactId>jerry-web</artifactId>
    <version>0.6.1</version>
</dependency>
```

Versioning
----------

For transparency and insight into our release cycle, and for striving to maintain backward compatibility, 
`jerry-web` will be maintained under the Semantic Versioning guidelines as much as possible.

Releases will be numbered with the follow format:

`<major>.<minor>.<patch>`

And constructed with the following guidelines:

* Breaking backward compatibility bumps the major
* New additions without breaking backward compatibility bumps the minor
* Bug fixes and misc changes bump the patch

For more information on SemVer, please visit http://semver.org/.

License
-------

```
Copyright (c) 2012-2014, Sandeep Gupta

The project uses various other libraries that are subject to their
own license terms. See the distribution libraries or the project
documentation for more details.

The entire source is licensed under the Apache License, Version 2.0 
(the "License"); you may not use this work except in compliance with
the LICENSE. You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
