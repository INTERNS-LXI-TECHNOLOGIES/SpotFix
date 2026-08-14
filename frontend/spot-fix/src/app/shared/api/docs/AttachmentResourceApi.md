# AttachmentResourceApi

All URIs are relative to *http://localhost:8081*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**countAttachments**](AttachmentResourceApi.md#countattachments) | **GET** /api/attachments/count |  |
| [**createAttachment**](AttachmentResourceApi.md#createattachment) | **POST** /api/attachments |  |
| [**deleteAttachment**](AttachmentResourceApi.md#deleteattachment) | **DELETE** /api/attachments/{id} |  |
| [**getAllAttachments**](AttachmentResourceApi.md#getallattachments) | **GET** /api/attachments |  |
| [**getAttachment**](AttachmentResourceApi.md#getattachment) | **GET** /api/attachments/{id} |  |
| [**partialUpdateAttachment**](AttachmentResourceApi.md#partialupdateattachment) | **PATCH** /api/attachments/{id} |  |
| [**updateAttachment**](AttachmentResourceApi.md#updateattachment) | **PUT** /api/attachments/{id} |  |



## countAttachments

> number countAttachments(idGreaterThan, idLessThan, idGreaterThanOrEqual, idLessThanOrEqual, idEquals, idNotEquals, idSpecified, idIn, idNotIn, attachmentTypeEquals, attachmentTypeNotEquals, attachmentTypeSpecified, attachmentTypeIn, attachmentTypeNotIn, fileNameContains, fileNameDoesNotContain, fileNameEquals, fileNameNotEquals, fileNameSpecified, fileNameIn, fileNameNotIn, filePathContains, filePathDoesNotContain, filePathEquals, filePathNotEquals, filePathSpecified, filePathIn, filePathNotIn, fileTypeContains, fileTypeDoesNotContain, fileTypeEquals, fileTypeNotEquals, fileTypeSpecified, fileTypeIn, fileTypeNotIn, fileSizeGreaterThan, fileSizeLessThan, fileSizeGreaterThanOrEqual, fileSizeLessThanOrEqual, fileSizeEquals, fileSizeNotEquals, fileSizeSpecified, fileSizeIn, fileSizeNotIn, checksumContains, checksumDoesNotContain, checksumEquals, checksumNotEquals, checksumSpecified, checksumIn, checksumNotIn, uploadedDateGreaterThan, uploadedDateLessThan, uploadedDateGreaterThanOrEqual, uploadedDateLessThanOrEqual, uploadedDateEquals, uploadedDateNotEquals, uploadedDateSpecified, uploadedDateIn, uploadedDateNotIn, durationSecondsGreaterThan, durationSecondsLessThan, durationSecondsGreaterThanOrEqual, durationSecondsLessThanOrEqual, durationSecondsEquals, durationSecondsNotEquals, durationSecondsSpecified, durationSecondsIn, durationSecondsNotIn, languageContains, languageDoesNotContain, languageEquals, languageNotEquals, languageSpecified, languageIn, languageNotIn, deletedEquals, deletedNotEquals, deletedSpecified, deletedIn, deletedNotIn, updatedDateGreaterThan, updatedDateLessThan, updatedDateGreaterThanOrEqual, updatedDateLessThanOrEqual, updatedDateEquals, updatedDateNotEquals, updatedDateSpecified, updatedDateIn, updatedDateNotIn, deletedDateGreaterThan, deletedDateLessThan, deletedDateGreaterThanOrEqual, deletedDateLessThanOrEqual, deletedDateEquals, deletedDateNotEquals, deletedDateSpecified, deletedDateIn, deletedDateNotIn, ticketIdGreaterThan, ticketIdLessThan, ticketIdGreaterThanOrEqual, ticketIdLessThanOrEqual, ticketIdEquals, ticketIdNotEquals, ticketIdSpecified, ticketIdIn, ticketIdNotIn, uploadedByIdGreaterThan, uploadedByIdLessThan, uploadedByIdGreaterThanOrEqual, uploadedByIdLessThanOrEqual, uploadedByIdEquals, uploadedByIdNotEquals, uploadedByIdSpecified, uploadedByIdIn, uploadedByIdNotIn, distinct)



### Example

```ts
import {
  Configuration,
  AttachmentResourceApi,
} from '';
import type { CountAttachmentsRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new AttachmentResourceApi();

  const body = {
    // number (optional)
    idGreaterThan: 789,
    // number (optional)
    idLessThan: 789,
    // number (optional)
    idGreaterThanOrEqual: 789,
    // number (optional)
    idLessThanOrEqual: 789,
    // number (optional)
    idEquals: 789,
    // number (optional)
    idNotEquals: 789,
    // boolean (optional)
    idSpecified: true,
    // Array<number> (optional)
    idIn: ...,
    // Array<number> (optional)
    idNotIn: ...,
    // 'IMAGE' | 'VIDEO' | 'AUDIO' | 'PDF' | 'DOCUMENT' | 'CONTRACTOR_UPDATE' | 'COMPLETION_REPORT' (optional)
    attachmentTypeEquals: attachmentTypeEquals_example,
    // 'IMAGE' | 'VIDEO' | 'AUDIO' | 'PDF' | 'DOCUMENT' | 'CONTRACTOR_UPDATE' | 'COMPLETION_REPORT' (optional)
    attachmentTypeNotEquals: attachmentTypeNotEquals_example,
    // boolean (optional)
    attachmentTypeSpecified: true,
    // Array<'IMAGE' | 'VIDEO' | 'AUDIO' | 'PDF' | 'DOCUMENT' | 'CONTRACTOR_UPDATE' | 'COMPLETION_REPORT'> (optional)
    attachmentTypeIn: ...,
    // Array<'IMAGE' | 'VIDEO' | 'AUDIO' | 'PDF' | 'DOCUMENT' | 'CONTRACTOR_UPDATE' | 'COMPLETION_REPORT'> (optional)
    attachmentTypeNotIn: ...,
    // string (optional)
    fileNameContains: fileNameContains_example,
    // string (optional)
    fileNameDoesNotContain: fileNameDoesNotContain_example,
    // string (optional)
    fileNameEquals: fileNameEquals_example,
    // string (optional)
    fileNameNotEquals: fileNameNotEquals_example,
    // boolean (optional)
    fileNameSpecified: true,
    // Array<string> (optional)
    fileNameIn: ...,
    // Array<string> (optional)
    fileNameNotIn: ...,
    // string (optional)
    filePathContains: filePathContains_example,
    // string (optional)
    filePathDoesNotContain: filePathDoesNotContain_example,
    // string (optional)
    filePathEquals: filePathEquals_example,
    // string (optional)
    filePathNotEquals: filePathNotEquals_example,
    // boolean (optional)
    filePathSpecified: true,
    // Array<string> (optional)
    filePathIn: ...,
    // Array<string> (optional)
    filePathNotIn: ...,
    // string (optional)
    fileTypeContains: fileTypeContains_example,
    // string (optional)
    fileTypeDoesNotContain: fileTypeDoesNotContain_example,
    // string (optional)
    fileTypeEquals: fileTypeEquals_example,
    // string (optional)
    fileTypeNotEquals: fileTypeNotEquals_example,
    // boolean (optional)
    fileTypeSpecified: true,
    // Array<string> (optional)
    fileTypeIn: ...,
    // Array<string> (optional)
    fileTypeNotIn: ...,
    // number (optional)
    fileSizeGreaterThan: 789,
    // number (optional)
    fileSizeLessThan: 789,
    // number (optional)
    fileSizeGreaterThanOrEqual: 789,
    // number (optional)
    fileSizeLessThanOrEqual: 789,
    // number (optional)
    fileSizeEquals: 789,
    // number (optional)
    fileSizeNotEquals: 789,
    // boolean (optional)
    fileSizeSpecified: true,
    // Array<number> (optional)
    fileSizeIn: ...,
    // Array<number> (optional)
    fileSizeNotIn: ...,
    // string (optional)
    checksumContains: checksumContains_example,
    // string (optional)
    checksumDoesNotContain: checksumDoesNotContain_example,
    // string (optional)
    checksumEquals: checksumEquals_example,
    // string (optional)
    checksumNotEquals: checksumNotEquals_example,
    // boolean (optional)
    checksumSpecified: true,
    // Array<string> (optional)
    checksumIn: ...,
    // Array<string> (optional)
    checksumNotIn: ...,
    // Date (optional)
    uploadedDateGreaterThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    uploadedDateLessThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    uploadedDateGreaterThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    uploadedDateLessThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    uploadedDateEquals: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    uploadedDateNotEquals: 2013-10-20T19:20:30+01:00,
    // boolean (optional)
    uploadedDateSpecified: true,
    // Array<Date> (optional)
    uploadedDateIn: ...,
    // Array<Date> (optional)
    uploadedDateNotIn: ...,
    // number (optional)
    durationSecondsGreaterThan: 56,
    // number (optional)
    durationSecondsLessThan: 56,
    // number (optional)
    durationSecondsGreaterThanOrEqual: 56,
    // number (optional)
    durationSecondsLessThanOrEqual: 56,
    // number (optional)
    durationSecondsEquals: 56,
    // number (optional)
    durationSecondsNotEquals: 56,
    // boolean (optional)
    durationSecondsSpecified: true,
    // Array<number> (optional)
    durationSecondsIn: ...,
    // Array<number> (optional)
    durationSecondsNotIn: ...,
    // string (optional)
    languageContains: languageContains_example,
    // string (optional)
    languageDoesNotContain: languageDoesNotContain_example,
    // string (optional)
    languageEquals: languageEquals_example,
    // string (optional)
    languageNotEquals: languageNotEquals_example,
    // boolean (optional)
    languageSpecified: true,
    // Array<string> (optional)
    languageIn: ...,
    // Array<string> (optional)
    languageNotIn: ...,
    // boolean (optional)
    deletedEquals: true,
    // boolean (optional)
    deletedNotEquals: true,
    // boolean (optional)
    deletedSpecified: true,
    // Array<boolean> (optional)
    deletedIn: ...,
    // Array<boolean> (optional)
    deletedNotIn: ...,
    // Date (optional)
    updatedDateGreaterThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    updatedDateLessThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    updatedDateGreaterThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    updatedDateLessThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    updatedDateEquals: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    updatedDateNotEquals: 2013-10-20T19:20:30+01:00,
    // boolean (optional)
    updatedDateSpecified: true,
    // Array<Date> (optional)
    updatedDateIn: ...,
    // Array<Date> (optional)
    updatedDateNotIn: ...,
    // Date (optional)
    deletedDateGreaterThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    deletedDateLessThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    deletedDateGreaterThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    deletedDateLessThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    deletedDateEquals: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    deletedDateNotEquals: 2013-10-20T19:20:30+01:00,
    // boolean (optional)
    deletedDateSpecified: true,
    // Array<Date> (optional)
    deletedDateIn: ...,
    // Array<Date> (optional)
    deletedDateNotIn: ...,
    // number (optional)
    ticketIdGreaterThan: 789,
    // number (optional)
    ticketIdLessThan: 789,
    // number (optional)
    ticketIdGreaterThanOrEqual: 789,
    // number (optional)
    ticketIdLessThanOrEqual: 789,
    // number (optional)
    ticketIdEquals: 789,
    // number (optional)
    ticketIdNotEquals: 789,
    // boolean (optional)
    ticketIdSpecified: true,
    // Array<number> (optional)
    ticketIdIn: ...,
    // Array<number> (optional)
    ticketIdNotIn: ...,
    // number (optional)
    uploadedByIdGreaterThan: 789,
    // number (optional)
    uploadedByIdLessThan: 789,
    // number (optional)
    uploadedByIdGreaterThanOrEqual: 789,
    // number (optional)
    uploadedByIdLessThanOrEqual: 789,
    // number (optional)
    uploadedByIdEquals: 789,
    // number (optional)
    uploadedByIdNotEquals: 789,
    // boolean (optional)
    uploadedByIdSpecified: true,
    // Array<number> (optional)
    uploadedByIdIn: ...,
    // Array<number> (optional)
    uploadedByIdNotIn: ...,
    // boolean (optional)
    distinct: true,
  } satisfies CountAttachmentsRequest;

  try {
    const data = await api.countAttachments(body);
    console.log(data);
  } catch (error) {
    console.error(error);
  }
}

// Run the test
example().catch(console.error);
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **idGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **idLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **idGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **idLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **idEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **idNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **idSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **idIn** | `Array<number>` |  | [Optional] |
| **idNotIn** | `Array<number>` |  | [Optional] |
| **attachmentTypeEquals** | `IMAGE`, `VIDEO`, `AUDIO`, `PDF`, `DOCUMENT`, `CONTRACTOR_UPDATE`, `COMPLETION_REPORT` |  | [Optional] [Defaults to `undefined`] [Enum: IMAGE, VIDEO, AUDIO, PDF, DOCUMENT, CONTRACTOR_UPDATE, COMPLETION_REPORT] |
| **attachmentTypeNotEquals** | `IMAGE`, `VIDEO`, `AUDIO`, `PDF`, `DOCUMENT`, `CONTRACTOR_UPDATE`, `COMPLETION_REPORT` |  | [Optional] [Defaults to `undefined`] [Enum: IMAGE, VIDEO, AUDIO, PDF, DOCUMENT, CONTRACTOR_UPDATE, COMPLETION_REPORT] |
| **attachmentTypeSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **attachmentTypeIn** | `IMAGE`, `VIDEO`, `AUDIO`, `PDF`, `DOCUMENT`, `CONTRACTOR_UPDATE`, `COMPLETION_REPORT` |  | [Optional] [Enum: IMAGE, VIDEO, AUDIO, PDF, DOCUMENT, CONTRACTOR_UPDATE, COMPLETION_REPORT] |
| **attachmentTypeNotIn** | `IMAGE`, `VIDEO`, `AUDIO`, `PDF`, `DOCUMENT`, `CONTRACTOR_UPDATE`, `COMPLETION_REPORT` |  | [Optional] [Enum: IMAGE, VIDEO, AUDIO, PDF, DOCUMENT, CONTRACTOR_UPDATE, COMPLETION_REPORT] |
| **fileNameContains** | `string` |  | [Optional] [Defaults to `undefined`] |
| **fileNameDoesNotContain** | `string` |  | [Optional] [Defaults to `undefined`] |
| **fileNameEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **fileNameNotEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **fileNameSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **fileNameIn** | `Array<string>` |  | [Optional] |
| **fileNameNotIn** | `Array<string>` |  | [Optional] |
| **filePathContains** | `string` |  | [Optional] [Defaults to `undefined`] |
| **filePathDoesNotContain** | `string` |  | [Optional] [Defaults to `undefined`] |
| **filePathEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **filePathNotEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **filePathSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **filePathIn** | `Array<string>` |  | [Optional] |
| **filePathNotIn** | `Array<string>` |  | [Optional] |
| **fileTypeContains** | `string` |  | [Optional] [Defaults to `undefined`] |
| **fileTypeDoesNotContain** | `string` |  | [Optional] [Defaults to `undefined`] |
| **fileTypeEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **fileTypeNotEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **fileTypeSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **fileTypeIn** | `Array<string>` |  | [Optional] |
| **fileTypeNotIn** | `Array<string>` |  | [Optional] |
| **fileSizeGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **fileSizeLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **fileSizeGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **fileSizeLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **fileSizeEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **fileSizeNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **fileSizeSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **fileSizeIn** | `Array<number>` |  | [Optional] |
| **fileSizeNotIn** | `Array<number>` |  | [Optional] |
| **checksumContains** | `string` |  | [Optional] [Defaults to `undefined`] |
| **checksumDoesNotContain** | `string` |  | [Optional] [Defaults to `undefined`] |
| **checksumEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **checksumNotEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **checksumSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **checksumIn** | `Array<string>` |  | [Optional] |
| **checksumNotIn** | `Array<string>` |  | [Optional] |
| **uploadedDateGreaterThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **uploadedDateLessThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **uploadedDateGreaterThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **uploadedDateLessThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **uploadedDateEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **uploadedDateNotEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **uploadedDateSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **uploadedDateIn** | `Array<Date>` |  | [Optional] |
| **uploadedDateNotIn** | `Array<Date>` |  | [Optional] |
| **durationSecondsGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **durationSecondsLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **durationSecondsGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **durationSecondsLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **durationSecondsEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **durationSecondsNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **durationSecondsSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **durationSecondsIn** | `Array<number>` |  | [Optional] |
| **durationSecondsNotIn** | `Array<number>` |  | [Optional] |
| **languageContains** | `string` |  | [Optional] [Defaults to `undefined`] |
| **languageDoesNotContain** | `string` |  | [Optional] [Defaults to `undefined`] |
| **languageEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **languageNotEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **languageSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **languageIn** | `Array<string>` |  | [Optional] |
| **languageNotIn** | `Array<string>` |  | [Optional] |
| **deletedEquals** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **deletedNotEquals** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **deletedSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **deletedIn** | `Array<boolean>` |  | [Optional] |
| **deletedNotIn** | `Array<boolean>` |  | [Optional] |
| **updatedDateGreaterThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **updatedDateLessThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **updatedDateGreaterThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **updatedDateLessThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **updatedDateEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **updatedDateNotEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **updatedDateSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **updatedDateIn** | `Array<Date>` |  | [Optional] |
| **updatedDateNotIn** | `Array<Date>` |  | [Optional] |
| **deletedDateGreaterThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **deletedDateLessThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **deletedDateGreaterThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **deletedDateLessThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **deletedDateEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **deletedDateNotEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **deletedDateSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **deletedDateIn** | `Array<Date>` |  | [Optional] |
| **deletedDateNotIn** | `Array<Date>` |  | [Optional] |
| **ticketIdGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **ticketIdLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **ticketIdGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **ticketIdLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **ticketIdEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **ticketIdNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **ticketIdSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **ticketIdIn** | `Array<number>` |  | [Optional] |
| **ticketIdNotIn** | `Array<number>` |  | [Optional] |
| **uploadedByIdGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **uploadedByIdLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **uploadedByIdGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **uploadedByIdLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **uploadedByIdEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **uploadedByIdNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **uploadedByIdSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **uploadedByIdIn** | `Array<number>` |  | [Optional] |
| **uploadedByIdNotIn** | `Array<number>` |  | [Optional] |
| **distinct** | `boolean` |  | [Optional] [Defaults to `undefined`] |

### Return type

**number**

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: `*/*`


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


## createAttachment

> AttachmentDTO createAttachment(attachmentDTO)



### Example

```ts
import {
  Configuration,
  AttachmentResourceApi,
} from '';
import type { CreateAttachmentRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new AttachmentResourceApi();

  const body = {
    // AttachmentDTO
    attachmentDTO: ...,
  } satisfies CreateAttachmentRequest;

  try {
    const data = await api.createAttachment(body);
    console.log(data);
  } catch (error) {
    console.error(error);
  }
}

// Run the test
example().catch(console.error);
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **attachmentDTO** | [AttachmentDTO](AttachmentDTO.md) |  | |

### Return type

[**AttachmentDTO**](AttachmentDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: `application/json`
- **Accept**: `*/*`


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


## deleteAttachment

> deleteAttachment(id)



### Example

```ts
import {
  Configuration,
  AttachmentResourceApi,
} from '';
import type { DeleteAttachmentRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new AttachmentResourceApi();

  const body = {
    // number
    id: 789,
  } satisfies DeleteAttachmentRequest;

  try {
    const data = await api.deleteAttachment(body);
    console.log(data);
  } catch (error) {
    console.error(error);
  }
}

// Run the test
example().catch(console.error);
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | `number` |  | [Defaults to `undefined`] |

### Return type

`void` (Empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


## getAllAttachments

> Array&lt;AttachmentDTO&gt; getAllAttachments(idGreaterThan, idLessThan, idGreaterThanOrEqual, idLessThanOrEqual, idEquals, idNotEquals, idSpecified, idIn, idNotIn, attachmentTypeEquals, attachmentTypeNotEquals, attachmentTypeSpecified, attachmentTypeIn, attachmentTypeNotIn, fileNameContains, fileNameDoesNotContain, fileNameEquals, fileNameNotEquals, fileNameSpecified, fileNameIn, fileNameNotIn, filePathContains, filePathDoesNotContain, filePathEquals, filePathNotEquals, filePathSpecified, filePathIn, filePathNotIn, fileTypeContains, fileTypeDoesNotContain, fileTypeEquals, fileTypeNotEquals, fileTypeSpecified, fileTypeIn, fileTypeNotIn, fileSizeGreaterThan, fileSizeLessThan, fileSizeGreaterThanOrEqual, fileSizeLessThanOrEqual, fileSizeEquals, fileSizeNotEquals, fileSizeSpecified, fileSizeIn, fileSizeNotIn, checksumContains, checksumDoesNotContain, checksumEquals, checksumNotEquals, checksumSpecified, checksumIn, checksumNotIn, uploadedDateGreaterThan, uploadedDateLessThan, uploadedDateGreaterThanOrEqual, uploadedDateLessThanOrEqual, uploadedDateEquals, uploadedDateNotEquals, uploadedDateSpecified, uploadedDateIn, uploadedDateNotIn, durationSecondsGreaterThan, durationSecondsLessThan, durationSecondsGreaterThanOrEqual, durationSecondsLessThanOrEqual, durationSecondsEquals, durationSecondsNotEquals, durationSecondsSpecified, durationSecondsIn, durationSecondsNotIn, languageContains, languageDoesNotContain, languageEquals, languageNotEquals, languageSpecified, languageIn, languageNotIn, deletedEquals, deletedNotEquals, deletedSpecified, deletedIn, deletedNotIn, updatedDateGreaterThan, updatedDateLessThan, updatedDateGreaterThanOrEqual, updatedDateLessThanOrEqual, updatedDateEquals, updatedDateNotEquals, updatedDateSpecified, updatedDateIn, updatedDateNotIn, deletedDateGreaterThan, deletedDateLessThan, deletedDateGreaterThanOrEqual, deletedDateLessThanOrEqual, deletedDateEquals, deletedDateNotEquals, deletedDateSpecified, deletedDateIn, deletedDateNotIn, ticketIdGreaterThan, ticketIdLessThan, ticketIdGreaterThanOrEqual, ticketIdLessThanOrEqual, ticketIdEquals, ticketIdNotEquals, ticketIdSpecified, ticketIdIn, ticketIdNotIn, uploadedByIdGreaterThan, uploadedByIdLessThan, uploadedByIdGreaterThanOrEqual, uploadedByIdLessThanOrEqual, uploadedByIdEquals, uploadedByIdNotEquals, uploadedByIdSpecified, uploadedByIdIn, uploadedByIdNotIn, distinct, page, size, sort)



### Example

```ts
import {
  Configuration,
  AttachmentResourceApi,
} from '';
import type { GetAllAttachmentsRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new AttachmentResourceApi();

  const body = {
    // number (optional)
    idGreaterThan: 789,
    // number (optional)
    idLessThan: 789,
    // number (optional)
    idGreaterThanOrEqual: 789,
    // number (optional)
    idLessThanOrEqual: 789,
    // number (optional)
    idEquals: 789,
    // number (optional)
    idNotEquals: 789,
    // boolean (optional)
    idSpecified: true,
    // Array<number> (optional)
    idIn: ...,
    // Array<number> (optional)
    idNotIn: ...,
    // 'IMAGE' | 'VIDEO' | 'AUDIO' | 'PDF' | 'DOCUMENT' | 'CONTRACTOR_UPDATE' | 'COMPLETION_REPORT' (optional)
    attachmentTypeEquals: attachmentTypeEquals_example,
    // 'IMAGE' | 'VIDEO' | 'AUDIO' | 'PDF' | 'DOCUMENT' | 'CONTRACTOR_UPDATE' | 'COMPLETION_REPORT' (optional)
    attachmentTypeNotEquals: attachmentTypeNotEquals_example,
    // boolean (optional)
    attachmentTypeSpecified: true,
    // Array<'IMAGE' | 'VIDEO' | 'AUDIO' | 'PDF' | 'DOCUMENT' | 'CONTRACTOR_UPDATE' | 'COMPLETION_REPORT'> (optional)
    attachmentTypeIn: ...,
    // Array<'IMAGE' | 'VIDEO' | 'AUDIO' | 'PDF' | 'DOCUMENT' | 'CONTRACTOR_UPDATE' | 'COMPLETION_REPORT'> (optional)
    attachmentTypeNotIn: ...,
    // string (optional)
    fileNameContains: fileNameContains_example,
    // string (optional)
    fileNameDoesNotContain: fileNameDoesNotContain_example,
    // string (optional)
    fileNameEquals: fileNameEquals_example,
    // string (optional)
    fileNameNotEquals: fileNameNotEquals_example,
    // boolean (optional)
    fileNameSpecified: true,
    // Array<string> (optional)
    fileNameIn: ...,
    // Array<string> (optional)
    fileNameNotIn: ...,
    // string (optional)
    filePathContains: filePathContains_example,
    // string (optional)
    filePathDoesNotContain: filePathDoesNotContain_example,
    // string (optional)
    filePathEquals: filePathEquals_example,
    // string (optional)
    filePathNotEquals: filePathNotEquals_example,
    // boolean (optional)
    filePathSpecified: true,
    // Array<string> (optional)
    filePathIn: ...,
    // Array<string> (optional)
    filePathNotIn: ...,
    // string (optional)
    fileTypeContains: fileTypeContains_example,
    // string (optional)
    fileTypeDoesNotContain: fileTypeDoesNotContain_example,
    // string (optional)
    fileTypeEquals: fileTypeEquals_example,
    // string (optional)
    fileTypeNotEquals: fileTypeNotEquals_example,
    // boolean (optional)
    fileTypeSpecified: true,
    // Array<string> (optional)
    fileTypeIn: ...,
    // Array<string> (optional)
    fileTypeNotIn: ...,
    // number (optional)
    fileSizeGreaterThan: 789,
    // number (optional)
    fileSizeLessThan: 789,
    // number (optional)
    fileSizeGreaterThanOrEqual: 789,
    // number (optional)
    fileSizeLessThanOrEqual: 789,
    // number (optional)
    fileSizeEquals: 789,
    // number (optional)
    fileSizeNotEquals: 789,
    // boolean (optional)
    fileSizeSpecified: true,
    // Array<number> (optional)
    fileSizeIn: ...,
    // Array<number> (optional)
    fileSizeNotIn: ...,
    // string (optional)
    checksumContains: checksumContains_example,
    // string (optional)
    checksumDoesNotContain: checksumDoesNotContain_example,
    // string (optional)
    checksumEquals: checksumEquals_example,
    // string (optional)
    checksumNotEquals: checksumNotEquals_example,
    // boolean (optional)
    checksumSpecified: true,
    // Array<string> (optional)
    checksumIn: ...,
    // Array<string> (optional)
    checksumNotIn: ...,
    // Date (optional)
    uploadedDateGreaterThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    uploadedDateLessThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    uploadedDateGreaterThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    uploadedDateLessThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    uploadedDateEquals: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    uploadedDateNotEquals: 2013-10-20T19:20:30+01:00,
    // boolean (optional)
    uploadedDateSpecified: true,
    // Array<Date> (optional)
    uploadedDateIn: ...,
    // Array<Date> (optional)
    uploadedDateNotIn: ...,
    // number (optional)
    durationSecondsGreaterThan: 56,
    // number (optional)
    durationSecondsLessThan: 56,
    // number (optional)
    durationSecondsGreaterThanOrEqual: 56,
    // number (optional)
    durationSecondsLessThanOrEqual: 56,
    // number (optional)
    durationSecondsEquals: 56,
    // number (optional)
    durationSecondsNotEquals: 56,
    // boolean (optional)
    durationSecondsSpecified: true,
    // Array<number> (optional)
    durationSecondsIn: ...,
    // Array<number> (optional)
    durationSecondsNotIn: ...,
    // string (optional)
    languageContains: languageContains_example,
    // string (optional)
    languageDoesNotContain: languageDoesNotContain_example,
    // string (optional)
    languageEquals: languageEquals_example,
    // string (optional)
    languageNotEquals: languageNotEquals_example,
    // boolean (optional)
    languageSpecified: true,
    // Array<string> (optional)
    languageIn: ...,
    // Array<string> (optional)
    languageNotIn: ...,
    // boolean (optional)
    deletedEquals: true,
    // boolean (optional)
    deletedNotEquals: true,
    // boolean (optional)
    deletedSpecified: true,
    // Array<boolean> (optional)
    deletedIn: ...,
    // Array<boolean> (optional)
    deletedNotIn: ...,
    // Date (optional)
    updatedDateGreaterThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    updatedDateLessThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    updatedDateGreaterThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    updatedDateLessThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    updatedDateEquals: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    updatedDateNotEquals: 2013-10-20T19:20:30+01:00,
    // boolean (optional)
    updatedDateSpecified: true,
    // Array<Date> (optional)
    updatedDateIn: ...,
    // Array<Date> (optional)
    updatedDateNotIn: ...,
    // Date (optional)
    deletedDateGreaterThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    deletedDateLessThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    deletedDateGreaterThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    deletedDateLessThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    deletedDateEquals: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    deletedDateNotEquals: 2013-10-20T19:20:30+01:00,
    // boolean (optional)
    deletedDateSpecified: true,
    // Array<Date> (optional)
    deletedDateIn: ...,
    // Array<Date> (optional)
    deletedDateNotIn: ...,
    // number (optional)
    ticketIdGreaterThan: 789,
    // number (optional)
    ticketIdLessThan: 789,
    // number (optional)
    ticketIdGreaterThanOrEqual: 789,
    // number (optional)
    ticketIdLessThanOrEqual: 789,
    // number (optional)
    ticketIdEquals: 789,
    // number (optional)
    ticketIdNotEquals: 789,
    // boolean (optional)
    ticketIdSpecified: true,
    // Array<number> (optional)
    ticketIdIn: ...,
    // Array<number> (optional)
    ticketIdNotIn: ...,
    // number (optional)
    uploadedByIdGreaterThan: 789,
    // number (optional)
    uploadedByIdLessThan: 789,
    // number (optional)
    uploadedByIdGreaterThanOrEqual: 789,
    // number (optional)
    uploadedByIdLessThanOrEqual: 789,
    // number (optional)
    uploadedByIdEquals: 789,
    // number (optional)
    uploadedByIdNotEquals: 789,
    // boolean (optional)
    uploadedByIdSpecified: true,
    // Array<number> (optional)
    uploadedByIdIn: ...,
    // Array<number> (optional)
    uploadedByIdNotIn: ...,
    // boolean (optional)
    distinct: true,
    // number | Zero-based page index (0..N) (optional)
    page: 56,
    // number | The size of the page to be returned (optional)
    size: 56,
    // Array<string> | Sorting criteria in the format: property,(asc|desc). Default sort order is ascending. Multiple sort criteria are supported. (optional)
    sort: ...,
  } satisfies GetAllAttachmentsRequest;

  try {
    const data = await api.getAllAttachments(body);
    console.log(data);
  } catch (error) {
    console.error(error);
  }
}

// Run the test
example().catch(console.error);
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **idGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **idLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **idGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **idLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **idEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **idNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **idSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **idIn** | `Array<number>` |  | [Optional] |
| **idNotIn** | `Array<number>` |  | [Optional] |
| **attachmentTypeEquals** | `IMAGE`, `VIDEO`, `AUDIO`, `PDF`, `DOCUMENT`, `CONTRACTOR_UPDATE`, `COMPLETION_REPORT` |  | [Optional] [Defaults to `undefined`] [Enum: IMAGE, VIDEO, AUDIO, PDF, DOCUMENT, CONTRACTOR_UPDATE, COMPLETION_REPORT] |
| **attachmentTypeNotEquals** | `IMAGE`, `VIDEO`, `AUDIO`, `PDF`, `DOCUMENT`, `CONTRACTOR_UPDATE`, `COMPLETION_REPORT` |  | [Optional] [Defaults to `undefined`] [Enum: IMAGE, VIDEO, AUDIO, PDF, DOCUMENT, CONTRACTOR_UPDATE, COMPLETION_REPORT] |
| **attachmentTypeSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **attachmentTypeIn** | `IMAGE`, `VIDEO`, `AUDIO`, `PDF`, `DOCUMENT`, `CONTRACTOR_UPDATE`, `COMPLETION_REPORT` |  | [Optional] [Enum: IMAGE, VIDEO, AUDIO, PDF, DOCUMENT, CONTRACTOR_UPDATE, COMPLETION_REPORT] |
| **attachmentTypeNotIn** | `IMAGE`, `VIDEO`, `AUDIO`, `PDF`, `DOCUMENT`, `CONTRACTOR_UPDATE`, `COMPLETION_REPORT` |  | [Optional] [Enum: IMAGE, VIDEO, AUDIO, PDF, DOCUMENT, CONTRACTOR_UPDATE, COMPLETION_REPORT] |
| **fileNameContains** | `string` |  | [Optional] [Defaults to `undefined`] |
| **fileNameDoesNotContain** | `string` |  | [Optional] [Defaults to `undefined`] |
| **fileNameEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **fileNameNotEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **fileNameSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **fileNameIn** | `Array<string>` |  | [Optional] |
| **fileNameNotIn** | `Array<string>` |  | [Optional] |
| **filePathContains** | `string` |  | [Optional] [Defaults to `undefined`] |
| **filePathDoesNotContain** | `string` |  | [Optional] [Defaults to `undefined`] |
| **filePathEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **filePathNotEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **filePathSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **filePathIn** | `Array<string>` |  | [Optional] |
| **filePathNotIn** | `Array<string>` |  | [Optional] |
| **fileTypeContains** | `string` |  | [Optional] [Defaults to `undefined`] |
| **fileTypeDoesNotContain** | `string` |  | [Optional] [Defaults to `undefined`] |
| **fileTypeEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **fileTypeNotEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **fileTypeSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **fileTypeIn** | `Array<string>` |  | [Optional] |
| **fileTypeNotIn** | `Array<string>` |  | [Optional] |
| **fileSizeGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **fileSizeLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **fileSizeGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **fileSizeLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **fileSizeEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **fileSizeNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **fileSizeSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **fileSizeIn** | `Array<number>` |  | [Optional] |
| **fileSizeNotIn** | `Array<number>` |  | [Optional] |
| **checksumContains** | `string` |  | [Optional] [Defaults to `undefined`] |
| **checksumDoesNotContain** | `string` |  | [Optional] [Defaults to `undefined`] |
| **checksumEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **checksumNotEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **checksumSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **checksumIn** | `Array<string>` |  | [Optional] |
| **checksumNotIn** | `Array<string>` |  | [Optional] |
| **uploadedDateGreaterThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **uploadedDateLessThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **uploadedDateGreaterThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **uploadedDateLessThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **uploadedDateEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **uploadedDateNotEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **uploadedDateSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **uploadedDateIn** | `Array<Date>` |  | [Optional] |
| **uploadedDateNotIn** | `Array<Date>` |  | [Optional] |
| **durationSecondsGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **durationSecondsLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **durationSecondsGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **durationSecondsLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **durationSecondsEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **durationSecondsNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **durationSecondsSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **durationSecondsIn** | `Array<number>` |  | [Optional] |
| **durationSecondsNotIn** | `Array<number>` |  | [Optional] |
| **languageContains** | `string` |  | [Optional] [Defaults to `undefined`] |
| **languageDoesNotContain** | `string` |  | [Optional] [Defaults to `undefined`] |
| **languageEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **languageNotEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **languageSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **languageIn** | `Array<string>` |  | [Optional] |
| **languageNotIn** | `Array<string>` |  | [Optional] |
| **deletedEquals** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **deletedNotEquals** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **deletedSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **deletedIn** | `Array<boolean>` |  | [Optional] |
| **deletedNotIn** | `Array<boolean>` |  | [Optional] |
| **updatedDateGreaterThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **updatedDateLessThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **updatedDateGreaterThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **updatedDateLessThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **updatedDateEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **updatedDateNotEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **updatedDateSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **updatedDateIn** | `Array<Date>` |  | [Optional] |
| **updatedDateNotIn** | `Array<Date>` |  | [Optional] |
| **deletedDateGreaterThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **deletedDateLessThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **deletedDateGreaterThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **deletedDateLessThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **deletedDateEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **deletedDateNotEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **deletedDateSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **deletedDateIn** | `Array<Date>` |  | [Optional] |
| **deletedDateNotIn** | `Array<Date>` |  | [Optional] |
| **ticketIdGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **ticketIdLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **ticketIdGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **ticketIdLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **ticketIdEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **ticketIdNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **ticketIdSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **ticketIdIn** | `Array<number>` |  | [Optional] |
| **ticketIdNotIn** | `Array<number>` |  | [Optional] |
| **uploadedByIdGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **uploadedByIdLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **uploadedByIdGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **uploadedByIdLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **uploadedByIdEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **uploadedByIdNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **uploadedByIdSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **uploadedByIdIn** | `Array<number>` |  | [Optional] |
| **uploadedByIdNotIn** | `Array<number>` |  | [Optional] |
| **distinct** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **page** | `number` | Zero-based page index (0..N) | [Optional] [Defaults to `0`] |
| **size** | `number` | The size of the page to be returned | [Optional] [Defaults to `20`] |
| **sort** | `Array<string>` | Sorting criteria in the format: property,(asc|desc). Default sort order is ascending. Multiple sort criteria are supported. | [Optional] |

### Return type

[**Array&lt;AttachmentDTO&gt;**](AttachmentDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: `application/json`


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


## getAttachment

> AttachmentDTO getAttachment(id)



### Example

```ts
import {
  Configuration,
  AttachmentResourceApi,
} from '';
import type { GetAttachmentRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new AttachmentResourceApi();

  const body = {
    // number
    id: 789,
  } satisfies GetAttachmentRequest;

  try {
    const data = await api.getAttachment(body);
    console.log(data);
  } catch (error) {
    console.error(error);
  }
}

// Run the test
example().catch(console.error);
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | `number` |  | [Defaults to `undefined`] |

### Return type

[**AttachmentDTO**](AttachmentDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: `*/*`


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


## partialUpdateAttachment

> AttachmentDTO partialUpdateAttachment(id, attachmentDTO)



### Example

```ts
import {
  Configuration,
  AttachmentResourceApi,
} from '';
import type { PartialUpdateAttachmentRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new AttachmentResourceApi();

  const body = {
    // number
    id: 789,
    // AttachmentDTO
    attachmentDTO: ...,
  } satisfies PartialUpdateAttachmentRequest;

  try {
    const data = await api.partialUpdateAttachment(body);
    console.log(data);
  } catch (error) {
    console.error(error);
  }
}

// Run the test
example().catch(console.error);
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | `number` |  | [Defaults to `undefined`] |
| **attachmentDTO** | [AttachmentDTO](AttachmentDTO.md) |  | |

### Return type

[**AttachmentDTO**](AttachmentDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: `application/json`, `application/merge-patch+json`
- **Accept**: `*/*`


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


## updateAttachment

> AttachmentDTO updateAttachment(id, attachmentDTO)



### Example

```ts
import {
  Configuration,
  AttachmentResourceApi,
} from '';
import type { UpdateAttachmentRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new AttachmentResourceApi();

  const body = {
    // number
    id: 789,
    // AttachmentDTO
    attachmentDTO: ...,
  } satisfies UpdateAttachmentRequest;

  try {
    const data = await api.updateAttachment(body);
    console.log(data);
  } catch (error) {
    console.error(error);
  }
}

// Run the test
example().catch(console.error);
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | `number` |  | [Defaults to `undefined`] |
| **attachmentDTO** | [AttachmentDTO](AttachmentDTO.md) |  | |

### Return type

[**AttachmentDTO**](AttachmentDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: `application/json`
- **Accept**: `*/*`


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)

