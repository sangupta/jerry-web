# jerry-web

[![Travis](https://img.shields.io/travis/sangupta/jerry-web.svg)]()
[![Coveralls](https://img.shields.io/coveralls/sangupta/jerry-web.svg)]()
[![license](https://img.shields.io/github/license/sangupta/jerry-web.svg)]()

Common Java functionality for building web applications particularly working with `Servlets` and JSPs.

`jerry-web` is a module library for the uber `jerry` library project. This module provides helper functionality 
when writing Java web applications - such as common filters, request wrappers, and custom JSP tags. The utility
classes are now a part of the `jerry-core` project.

## Release Notes

**Release 1.1.0 (20 Dec 2017)**

* Added a `GsonTag` for converting object to JSON inside a JSP
* Fixed bug in `ExceptionCatchingFilter` to log actual UUID of error object
* Upgraded `JSoup` version to latest

**Release 1.0.0 (23 Dec 2016)**

* Removed all utility code that is now a part of the [jerry-core](https://github.com/sangupta/jerry-core) project
* Added unit testing of all JSP custom tags using the [am](https://github.com/sangupta/am) project
* Preparing for OSS release as **1.0.0**

**Release 0.6.1 (09 Nov 2014)**

* Fixed date/time to output as long value in `GsonJsonProvider`
* Added method to extract URI from ServletRequest to `RequestUtils`
* Added method to send byte[] downstream in `ResponseUtils`
* Added servlet filters
 * `JavascriptMinificationFilter` - uses Google closure compiler to minify JS
 * `JavascriptReorderingFilter` - reorder javascript tags inside HTML stream to push them to end of HTML file for faster loading of HTML page
 * `ExceptionCatchingFilter` - catch all uncaught exceptions and log them
* Added log guards and javadocs
* Added missing copyright headers

**Release 0.5.0 (07 Feb 2014)**

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

## Downloads

The library can be downloaded from Maven Central using:

```xml
<dependency>
    <groupId>com.sangupta</groupId>
    <artifactId>jerry-web</artifactId>
    <version>1.1.0</version>
</dependency>
```

## Versioning

For transparency and insight into our release cycle, and for striving to maintain backward compatibility, 
`jerry-web` will be maintained under the Semantic Versioning guidelines as much as possible.

Releases will be numbered with the follow format:

`<major>.<minor>.<patch>`

And constructed with the following guidelines:

* Breaking backward compatibility bumps the major
* New additions without breaking backward compatibility bumps the minor
* Bug fixes and misc changes bump the patch

For more information on SemVer, please visit http://semver.org/.

## License

```
jerry-web: Common Servlet/JSP Functionality
Copyright (c) 2012-2016, Sandeep Gupta

https://sangupta.com/projects/jerry-web

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
