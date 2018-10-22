Glossary
--------
| Terms                                     | Definition   |
| :---------------------------------------: |:-------------|
|**<a id="body-location"></a>Body location**| A reference to a photo indicating which area of the body the photo was taken on.
|**<a id="care-provider"></a>Care provider**| One of the [users](#user) of the application; care provider is a medical professional who monitors and tracks the changes of a [patient](#patient)'s problem overtime, and comments to a patient's problem records.
|**<a id="geo-location"></a>Geo location**  | Geographical location of a [record](#record) on a map.
|**HADA**                                   | Application's name; 肌 pronounced hada, is the japanese character for skin.
|**<a id="patient"></a>Patient**            | One of the users of the application; a patient is a person who is seeing a medical professional for a problem they have, who monitors and records changes of a problem overtime.
|**<a id="problem"></a>Problem**            | A medical problem that needs to be monitored and recorded by the [patient](#patient). It is denoted as a title, date started, and a brief description in the app.
|**Profile**                                | It is a unique user id with associated email address and phone number.
|**<a id="record"></a>Record**              | A record of change for a particular [problem](#problem) that is being tracked. It must have a timestamp, and can optionally have a [geo-location](#geo-location), a [body-location](#body-location), title, comment, photos, and a comment from the care provider.
|**Server**                                 | A back-end [elasticsearch](https://www.elastic.co/) database that stores user information and records of problems.
|**Sys admin**                              | A [system administrator](https://en.wikipedia.org/wiki/System_administrator), or sysadmin, is a person who is responsible for the upkeep, configuration, and reliable operation of computer systems; especially multi-user computers, such as servers. 
|**<a id="user"></a>User**                  | A person who uses the application; Either a [patient](#patient) or a [care provider](#care-provider)

Related products
----------------
* http://geniemd.com/
* https://www.andaman7.com
* http://www.ibluebutton.com/
* https://play.google.com/store/apps/details?id=com.cliniconline
* https://play.google.com/store/apps/details?id=vladimir.yerokhin.medicalrecord
