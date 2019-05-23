CHANGELOG
=========

v1.14.0
----
   * Field `birthdate` changed to `birthday` in `Userinfo` class [#312](https://github.com/paypal/PayPal-Java-SDK/pull/312).
   * Undeprecate `PaymentInstruction` with note for German merchants only [#314](https://github.com/paypal/PayPal-Java-SDK/pull/314).
   * Reduce IDE Warnings by cleaning up code [#292](https://github.com/paypal/PayPal-Java-SDK/pull/292).
   * Fix proxy being ignored by `SSLUtil.donwloadCertificateFromPath` [#276](https://github.com/paypal/PayPal-Java-SDK/issues/276).
   * Use gretty for sample [#297](https://github.com/paypal/PayPal-Java-SDK/pull/297).

v1.13.1
----
   * Fixed PATCH requests for Google App Engine [#273](https://github.com/paypal/PayPal-Java-SDK/pull/273).

v1.13.0
----
   * Payments API updates [#258](https://github.com/paypal/PayPal-Java-SDK/pull/258).

v1.12.0
----
   * Payment Experience API updates [#252](https://github.com/paypal/PayPal-Java-SDK/pull/252).
   * Payouts API updates [#249](https://github.com/paypal/PayPal-Java-SDK/pull/249).
   * Minor sample updates.

v1.11.0
----
   * Removed hard dependency on Log4j [#253](https://github.com/paypal/PayPal-Java-SDK/pull/253).

v1.10.0
----
   * Made apiContext threadsafe [#251](https://github.com/paypal/PayPal-Java-SDK/pull/251).
   * Updates to Invoicing APIs [#247](https://github.com/paypal/PayPal-Java-SDK/pull/247).
   * Updates to Webhooks APIs [#248](https://github.com/paypal/PayPal-Java-SDK/pull/248).

v1.9.3
----
   * Changed cvv2 to be a String [#242](https://github.com/paypal/PayPal-Java-SDK/pull/242).
   * Change OpenID Connect URLs [#236](https://github.com/paypal/PayPal-Java-SDK/pull/236).
   * Fixed functional tests and duplicate requestId [#228](https://github.com/paypal/PayPal-Java-SDK/pull/228).
   * Minor bug fixes [#232](https://github.com/paypal/PayPal-Java-SDK/pull/232).

v1.9.2
----
   * Improvements to Webhook Validation integration [#223](https://github.com/paypal/PayPal-Java-SDK/pull/223).
   * Minor improvements.

v1.9.1
----
   * Use UTF-8 as the character set when generating CRC32 checksum [#218](https://github.com/paypal/PayPal-Java-SDK/pull/218).

v1.9.0
------
   * Updated Invoicing APIs [#215](https://github.com/paypal/PayPal-Java-SDK/pull/215).
   * `toString` now returns a JSON response [#213](https://github.com/paypal/PayPal-Java-SDK/pull/213).

v1.8.1
------
   * Improved OpenID integration[#209](https://github.com/paypal/PayPal-Java-SDK/pull/209).
   * Fixed javadocs warnings [#206](https://github.com/paypal/PayPal-Java-SDK/pull/206).

v1.8.0
------
   * Updated APIContext for ease of integration.
   * General code improvements [#199](https://github.com/paypal/PayPal-Java-SDK/pull/199).
   * Removed unused code, and minor refactoring.
   * Removed the need for sdk_config.properties file.
   * Set headers properly in HTTPConnection [#201](https://github.com/paypal/PayPal-Java-SDK/pull/201).

v1.7.0
------
   * Updated README.md documentations [#190](https://github.com/paypal/PayPal-Java-SDK/pull/190).
   * Major code cleanup [#187](https://github.com/paypal/PayPal-Java-SDK/pull/187).
   * Added logic to re-generate token automatically [#186](https://github.com/paypal/PayPal-Java-SDK/pull/186).
   * Removed dependency to load hardcoded configuration file [#182](https://github.com/paypal/PayPal-Java-SDK/pull/182).
   * Migrate to Gradle based project [#181](https://github.com/paypal/PayPal-Java-SDK/pull/181).

v1.6.0
------
   * Fixed JSON syntax error.
   * Fixed subscription sample.
   * Enabled third party invoicing.

v1.5.0
------
   * Vault API update.
   * Updated prerequisites.
   * Added custom-headers.
   
v1.4.2
------
   * Fix null pointer exception. Fixes #150.

v1.4.1
------
   * Fix webhook common name verification.

v1.4.0
------
   * Payment API update.
   * Update TLS 1.2 warning message.

v1.3.1
-------
   * Updated test cases.
   * Disabled Java 8 doclint.
   
v1.3.0
-------
   * Reverted empty list behavior when HTTP 204 (no content) is returned [#124](https://github.com/paypal/PayPal-Java-SDK/issues/124) for backward compatibility.
   * Updated SSL protocol to TLS 1.2.
   * Added Java vendor to user-agent.
   * Update exception handling and error message printing.

v1.2.10
-------
   * Added `insurance` and `shipping_discount` to `Details` [#125](https://github.com/paypal/PayPal-Java-SDK/issues/125).
   * Merged [#123](https://github.com/paypal/PayPal-Java-SDK/issues/123) for improved error details.
   * Improved ClientActionRequireException capture to make error details programmatically accessible.
   * Return empty list when HTTP 204 (no content) is returned [#124](https://github.com/paypal/PayPal-Java-SDK/issues/124).
   * Fixed URL parameter formatting for refresh token exchange [#131](https://github.com/paypal/PayPal-Java-SDK/issues/131).
   * Code clean-up.

v1.2.9
------
   * Updated rest-api-sdk version on sample project.

v1.2.8
------
   * Updated error handling and messages for error response [#118](https://github.com/paypal/PayPal-Java-SDK/issues/118).
   * Added failover for mode in case configurationMap is null [#120](https://github.com/paypal/PayPal-Java-SDK/issues/120).
   * Added HTTP header case-insensitivity check to webhook validation.

v1.2.7
------
   * Enabled request/response logging only in non-live mode and log level set to DEBUG.
   * Added CURL command logging to easily test API behavior.
   * Added sample log4j2 configuration file.

v1.2.6
------
   * Version of com.paypal.sdk:rest-api-sdk updated.
   * Updated failure error message [#109](https://github.com/paypal/PayPal-Java-SDK/issues/109).
   * Fixed NPE on GAE [#111](https://github.com/paypal/PayPal-Java-SDK/issues/111).
   * Fixed CreditCard.list() [#112](https://github.com/paypal/PayPal-Java-SDK/issues/112).
   * Fixed empty APIContext [#113](https://github.com/paypal/PayPal-Java-SDK/issues/113).

v1.2.5 (June 19, 2015)
----------------------
   * Fixed dynamic config loading on GAE [#104](https://github.com/paypal/PayPal-Java-SDK/issues/104).
   * Fixed CreditCard.update() [#106](https://github.com/paypal/PayPal-Java-SDK/issues/106).
   * Fixed CreditCard.get() [#96](https://github.com/paypal/PayPal-Java-SDK/issues/96).

v1.2.4 (June 9, 2015)
---------------------
   * Added webhooks validation.

v1.2.3 (May 26, 2015)
---------------------
   * Fixed empty CVV2.

v1.2.2 (April 23, 2015)
-----------------------
   * Fixed IPNmessage behind proxy settings.
   * Made sdk_config.properties optional.

v1.2.1 (April 15, 2015)
-----------------------
   * Fixed error field in Payouts API.
   * Added time_stamp field in subscription.
   * Made SSL fields configurable.

v1.2.0 (March 10, 2015)
-----------------------
   * Changed default logger to slf4j for Google App Engine.
   * Payment API update.
   * Redesigned error model structure.

v1.1.2 (February 15, 2015)
--------------------------
   * Changed credit card CVV type from int to Integer.
   * Fixed maven artifact issue.
   * Fixed Agreement.execute() and updated Agreement.execute(APIContext, String).
   * Fixed test case issues.

v1.1.1 (February 10, 2015)
--------------------------
   * Added Payment.create().
   * Fixed UserInfo retrieval.

v1.1.0 (January 26, 2015)
-------------------------
   * Added Payout support.

v1.0.0 (January 12, 2014)
-------------------------
   * Merged sdk-core-java.
   * Replaced java.util.logging to log4j.
   * Fixed token expiration time.
   * Fixed agreement state.

v0.12.2 (December 5, 2014)
--------------------------
   * Grouped tests into 2 subsets: unit tests and integration tests.
   * Disabled integration tests by default.
 
v0.12.1 (November 25, 2014)
---------------------------
   * Fixed test case issues.
   * Grouped integration tests separately and disabled them by default.

v0.12.0 (November 24, 2014)
---------------------------
   * Added support for billing agreement and plan.
   * Updated user-agent header.

v0.11.2 (November 3, 2014)
--------------------------
   * Deactivated maven coverage profile.

v0.11.1 (October 31, 2014)
--------------------------
   * Undocumented.

v0.11.0 (October 30, 2014)
--------------------------
   * Undocumented.

v0.10.0 (October 30, 2014)
--------------------------
   * Added web profile support.

v0.9.1 (June 16, 2014)
----------------------
   * Updated core.
   * Made Java 6 compatible.

v0.9.0 (May 13, 2014)
-----------------------
   * Added future payment.
   * Updated core version.

v0.8.0 (April 16, 2014)
-----------------------
   * Added invoicing support.

v0.7.1 (July 31, 2013)
-----------------------
   * Added support for Reauthorization.

v0.7.0 (May 30, 2013)
-----------------------
   * Added support for Auth and Capture APIs.
   * Types Modified to match the API Spec.

v0.6.0 (April 26, 2013)
-----------------------
   * Added dynamic configuration support for API calls.

v0.5.2 (March 07, 2013)
-----------------------
   * Initial Release.


