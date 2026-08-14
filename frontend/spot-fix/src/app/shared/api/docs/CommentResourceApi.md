# CommentResourceApi

All URIs are relative to *http://localhost:8081*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**countComments**](CommentResourceApi.md#countcomments) | **GET** /api/comments/count |  |
| [**createComment**](CommentResourceApi.md#createcomment) | **POST** /api/comments |  |
| [**deleteComment**](CommentResourceApi.md#deletecomment) | **DELETE** /api/comments/{id} |  |
| [**getAllComments**](CommentResourceApi.md#getallcomments) | **GET** /api/comments |  |
| [**getComment**](CommentResourceApi.md#getcomment) | **GET** /api/comments/{id} |  |
| [**partialUpdateComment**](CommentResourceApi.md#partialupdatecomment) | **PATCH** /api/comments/{id} |  |
| [**updateComment**](CommentResourceApi.md#updatecomment) | **PUT** /api/comments/{id} |  |



## countComments

> number countComments(idGreaterThan, idLessThan, idGreaterThanOrEqual, idLessThanOrEqual, idEquals, idNotEquals, idSpecified, idIn, idNotIn, titleContains, titleDoesNotContain, titleEquals, titleNotEquals, titleSpecified, titleIn, titleNotIn, createdDateGreaterThan, createdDateLessThan, createdDateGreaterThanOrEqual, createdDateLessThanOrEqual, createdDateEquals, createdDateNotEquals, createdDateSpecified, createdDateIn, createdDateNotIn, updatedDateGreaterThan, updatedDateLessThan, updatedDateGreaterThanOrEqual, updatedDateLessThanOrEqual, updatedDateEquals, updatedDateNotEquals, updatedDateSpecified, updatedDateIn, updatedDateNotIn, deletedEquals, deletedNotEquals, deletedSpecified, deletedIn, deletedNotIn, deletedDateGreaterThan, deletedDateLessThan, deletedDateGreaterThanOrEqual, deletedDateLessThanOrEqual, deletedDateEquals, deletedDateNotEquals, deletedDateSpecified, deletedDateIn, deletedDateNotIn, ticketIdGreaterThan, ticketIdLessThan, ticketIdGreaterThanOrEqual, ticketIdLessThanOrEqual, ticketIdEquals, ticketIdNotEquals, ticketIdSpecified, ticketIdIn, ticketIdNotIn, userIdGreaterThan, userIdLessThan, userIdGreaterThanOrEqual, userIdLessThanOrEqual, userIdEquals, userIdNotEquals, userIdSpecified, userIdIn, userIdNotIn, distinct)



### Example

```ts
import {
  Configuration,
  CommentResourceApi,
} from '';
import type { CountCommentsRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new CommentResourceApi();

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
    // string (optional)
    titleContains: titleContains_example,
    // string (optional)
    titleDoesNotContain: titleDoesNotContain_example,
    // string (optional)
    titleEquals: titleEquals_example,
    // string (optional)
    titleNotEquals: titleNotEquals_example,
    // boolean (optional)
    titleSpecified: true,
    // Array<string> (optional)
    titleIn: ...,
    // Array<string> (optional)
    titleNotIn: ...,
    // Date (optional)
    createdDateGreaterThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    createdDateLessThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    createdDateGreaterThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    createdDateLessThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    createdDateEquals: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    createdDateNotEquals: 2013-10-20T19:20:30+01:00,
    // boolean (optional)
    createdDateSpecified: true,
    // Array<Date> (optional)
    createdDateIn: ...,
    // Array<Date> (optional)
    createdDateNotIn: ...,
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
    userIdGreaterThan: 789,
    // number (optional)
    userIdLessThan: 789,
    // number (optional)
    userIdGreaterThanOrEqual: 789,
    // number (optional)
    userIdLessThanOrEqual: 789,
    // number (optional)
    userIdEquals: 789,
    // number (optional)
    userIdNotEquals: 789,
    // boolean (optional)
    userIdSpecified: true,
    // Array<number> (optional)
    userIdIn: ...,
    // Array<number> (optional)
    userIdNotIn: ...,
    // boolean (optional)
    distinct: true,
  } satisfies CountCommentsRequest;

  try {
    const data = await api.countComments(body);
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
| **titleContains** | `string` |  | [Optional] [Defaults to `undefined`] |
| **titleDoesNotContain** | `string` |  | [Optional] [Defaults to `undefined`] |
| **titleEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **titleNotEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **titleSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **titleIn** | `Array<string>` |  | [Optional] |
| **titleNotIn** | `Array<string>` |  | [Optional] |
| **createdDateGreaterThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **createdDateLessThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **createdDateGreaterThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **createdDateLessThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **createdDateEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **createdDateNotEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **createdDateSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **createdDateIn** | `Array<Date>` |  | [Optional] |
| **createdDateNotIn** | `Array<Date>` |  | [Optional] |
| **updatedDateGreaterThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **updatedDateLessThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **updatedDateGreaterThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **updatedDateLessThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **updatedDateEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **updatedDateNotEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **updatedDateSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **updatedDateIn** | `Array<Date>` |  | [Optional] |
| **updatedDateNotIn** | `Array<Date>` |  | [Optional] |
| **deletedEquals** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **deletedNotEquals** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **deletedSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **deletedIn** | `Array<boolean>` |  | [Optional] |
| **deletedNotIn** | `Array<boolean>` |  | [Optional] |
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
| **userIdGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **userIdLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **userIdGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **userIdLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **userIdEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **userIdNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **userIdSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **userIdIn** | `Array<number>` |  | [Optional] |
| **userIdNotIn** | `Array<number>` |  | [Optional] |
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


## createComment

> CommentDTO createComment(commentDTO)



### Example

```ts
import {
  Configuration,
  CommentResourceApi,
} from '';
import type { CreateCommentRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new CommentResourceApi();

  const body = {
    // CommentDTO
    commentDTO: ...,
  } satisfies CreateCommentRequest;

  try {
    const data = await api.createComment(body);
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
| **commentDTO** | [CommentDTO](CommentDTO.md) |  | |

### Return type

[**CommentDTO**](CommentDTO.md)

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


## deleteComment

> deleteComment(id)



### Example

```ts
import {
  Configuration,
  CommentResourceApi,
} from '';
import type { DeleteCommentRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new CommentResourceApi();

  const body = {
    // number
    id: 789,
  } satisfies DeleteCommentRequest;

  try {
    const data = await api.deleteComment(body);
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


## getAllComments

> Array&lt;CommentDTO&gt; getAllComments(idGreaterThan, idLessThan, idGreaterThanOrEqual, idLessThanOrEqual, idEquals, idNotEquals, idSpecified, idIn, idNotIn, titleContains, titleDoesNotContain, titleEquals, titleNotEquals, titleSpecified, titleIn, titleNotIn, createdDateGreaterThan, createdDateLessThan, createdDateGreaterThanOrEqual, createdDateLessThanOrEqual, createdDateEquals, createdDateNotEquals, createdDateSpecified, createdDateIn, createdDateNotIn, updatedDateGreaterThan, updatedDateLessThan, updatedDateGreaterThanOrEqual, updatedDateLessThanOrEqual, updatedDateEquals, updatedDateNotEquals, updatedDateSpecified, updatedDateIn, updatedDateNotIn, deletedEquals, deletedNotEquals, deletedSpecified, deletedIn, deletedNotIn, deletedDateGreaterThan, deletedDateLessThan, deletedDateGreaterThanOrEqual, deletedDateLessThanOrEqual, deletedDateEquals, deletedDateNotEquals, deletedDateSpecified, deletedDateIn, deletedDateNotIn, ticketIdGreaterThan, ticketIdLessThan, ticketIdGreaterThanOrEqual, ticketIdLessThanOrEqual, ticketIdEquals, ticketIdNotEquals, ticketIdSpecified, ticketIdIn, ticketIdNotIn, userIdGreaterThan, userIdLessThan, userIdGreaterThanOrEqual, userIdLessThanOrEqual, userIdEquals, userIdNotEquals, userIdSpecified, userIdIn, userIdNotIn, distinct, page, size, sort)



### Example

```ts
import {
  Configuration,
  CommentResourceApi,
} from '';
import type { GetAllCommentsRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new CommentResourceApi();

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
    // string (optional)
    titleContains: titleContains_example,
    // string (optional)
    titleDoesNotContain: titleDoesNotContain_example,
    // string (optional)
    titleEquals: titleEquals_example,
    // string (optional)
    titleNotEquals: titleNotEquals_example,
    // boolean (optional)
    titleSpecified: true,
    // Array<string> (optional)
    titleIn: ...,
    // Array<string> (optional)
    titleNotIn: ...,
    // Date (optional)
    createdDateGreaterThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    createdDateLessThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    createdDateGreaterThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    createdDateLessThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    createdDateEquals: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    createdDateNotEquals: 2013-10-20T19:20:30+01:00,
    // boolean (optional)
    createdDateSpecified: true,
    // Array<Date> (optional)
    createdDateIn: ...,
    // Array<Date> (optional)
    createdDateNotIn: ...,
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
    userIdGreaterThan: 789,
    // number (optional)
    userIdLessThan: 789,
    // number (optional)
    userIdGreaterThanOrEqual: 789,
    // number (optional)
    userIdLessThanOrEqual: 789,
    // number (optional)
    userIdEquals: 789,
    // number (optional)
    userIdNotEquals: 789,
    // boolean (optional)
    userIdSpecified: true,
    // Array<number> (optional)
    userIdIn: ...,
    // Array<number> (optional)
    userIdNotIn: ...,
    // boolean (optional)
    distinct: true,
    // number | Zero-based page index (0..N) (optional)
    page: 56,
    // number | The size of the page to be returned (optional)
    size: 56,
    // Array<string> | Sorting criteria in the format: property,(asc|desc). Default sort order is ascending. Multiple sort criteria are supported. (optional)
    sort: ...,
  } satisfies GetAllCommentsRequest;

  try {
    const data = await api.getAllComments(body);
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
| **titleContains** | `string` |  | [Optional] [Defaults to `undefined`] |
| **titleDoesNotContain** | `string` |  | [Optional] [Defaults to `undefined`] |
| **titleEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **titleNotEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **titleSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **titleIn** | `Array<string>` |  | [Optional] |
| **titleNotIn** | `Array<string>` |  | [Optional] |
| **createdDateGreaterThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **createdDateLessThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **createdDateGreaterThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **createdDateLessThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **createdDateEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **createdDateNotEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **createdDateSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **createdDateIn** | `Array<Date>` |  | [Optional] |
| **createdDateNotIn** | `Array<Date>` |  | [Optional] |
| **updatedDateGreaterThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **updatedDateLessThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **updatedDateGreaterThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **updatedDateLessThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **updatedDateEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **updatedDateNotEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **updatedDateSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **updatedDateIn** | `Array<Date>` |  | [Optional] |
| **updatedDateNotIn** | `Array<Date>` |  | [Optional] |
| **deletedEquals** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **deletedNotEquals** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **deletedSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **deletedIn** | `Array<boolean>` |  | [Optional] |
| **deletedNotIn** | `Array<boolean>` |  | [Optional] |
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
| **userIdGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **userIdLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **userIdGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **userIdLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **userIdEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **userIdNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **userIdSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **userIdIn** | `Array<number>` |  | [Optional] |
| **userIdNotIn** | `Array<number>` |  | [Optional] |
| **distinct** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **page** | `number` | Zero-based page index (0..N) | [Optional] [Defaults to `0`] |
| **size** | `number` | The size of the page to be returned | [Optional] [Defaults to `20`] |
| **sort** | `Array<string>` | Sorting criteria in the format: property,(asc|desc). Default sort order is ascending. Multiple sort criteria are supported. | [Optional] |

### Return type

[**Array&lt;CommentDTO&gt;**](CommentDTO.md)

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


## getComment

> CommentDTO getComment(id)



### Example

```ts
import {
  Configuration,
  CommentResourceApi,
} from '';
import type { GetCommentRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new CommentResourceApi();

  const body = {
    // number
    id: 789,
  } satisfies GetCommentRequest;

  try {
    const data = await api.getComment(body);
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

[**CommentDTO**](CommentDTO.md)

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


## partialUpdateComment

> CommentDTO partialUpdateComment(id, commentDTO)



### Example

```ts
import {
  Configuration,
  CommentResourceApi,
} from '';
import type { PartialUpdateCommentRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new CommentResourceApi();

  const body = {
    // number
    id: 789,
    // CommentDTO
    commentDTO: ...,
  } satisfies PartialUpdateCommentRequest;

  try {
    const data = await api.partialUpdateComment(body);
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
| **commentDTO** | [CommentDTO](CommentDTO.md) |  | |

### Return type

[**CommentDTO**](CommentDTO.md)

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


## updateComment

> CommentDTO updateComment(id, commentDTO)



### Example

```ts
import {
  Configuration,
  CommentResourceApi,
} from '';
import type { UpdateCommentRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new CommentResourceApi();

  const body = {
    // number
    id: 789,
    // CommentDTO
    commentDTO: ...,
  } satisfies UpdateCommentRequest;

  try {
    const data = await api.updateComment(body);
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
| **commentDTO** | [CommentDTO](CommentDTO.md) |  | |

### Return type

[**CommentDTO**](CommentDTO.md)

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

