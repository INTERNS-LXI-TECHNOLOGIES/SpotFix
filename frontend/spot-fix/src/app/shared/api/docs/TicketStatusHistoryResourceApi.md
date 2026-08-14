# TicketStatusHistoryResourceApi

All URIs are relative to *http://localhost:8081*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**countTicketStatusHistories**](TicketStatusHistoryResourceApi.md#countticketstatushistories) | **GET** /api/ticket-status-histories/count |  |
| [**createTicketStatusHistory**](TicketStatusHistoryResourceApi.md#createticketstatushistory) | **POST** /api/ticket-status-histories |  |
| [**deleteTicketStatusHistory**](TicketStatusHistoryResourceApi.md#deleteticketstatushistory) | **DELETE** /api/ticket-status-histories/{id} |  |
| [**getAllTicketStatusHistories**](TicketStatusHistoryResourceApi.md#getallticketstatushistories) | **GET** /api/ticket-status-histories |  |
| [**getTicketStatusHistory**](TicketStatusHistoryResourceApi.md#getticketstatushistory) | **GET** /api/ticket-status-histories/{id} |  |
| [**partialUpdateTicketStatusHistory**](TicketStatusHistoryResourceApi.md#partialupdateticketstatushistory) | **PATCH** /api/ticket-status-histories/{id} |  |
| [**updateTicketStatusHistory**](TicketStatusHistoryResourceApi.md#updateticketstatushistory) | **PUT** /api/ticket-status-histories/{id} |  |



## countTicketStatusHistories

> number countTicketStatusHistories(idGreaterThan, idLessThan, idGreaterThanOrEqual, idLessThanOrEqual, idEquals, idNotEquals, idSpecified, idIn, idNotIn, oldStatusEquals, oldStatusNotEquals, oldStatusSpecified, oldStatusIn, oldStatusNotIn, newStatusEquals, newStatusNotEquals, newStatusSpecified, newStatusIn, newStatusNotIn, changedDateGreaterThan, changedDateLessThan, changedDateGreaterThanOrEqual, changedDateLessThanOrEqual, changedDateEquals, changedDateNotEquals, changedDateSpecified, changedDateIn, changedDateNotIn, ticketIdGreaterThan, ticketIdLessThan, ticketIdGreaterThanOrEqual, ticketIdLessThanOrEqual, ticketIdEquals, ticketIdNotEquals, ticketIdSpecified, ticketIdIn, ticketIdNotIn, changedByIdGreaterThan, changedByIdLessThan, changedByIdGreaterThanOrEqual, changedByIdLessThanOrEqual, changedByIdEquals, changedByIdNotEquals, changedByIdSpecified, changedByIdIn, changedByIdNotIn, distinct)



### Example

```ts
import {
  Configuration,
  TicketStatusHistoryResourceApi,
} from '';
import type { CountTicketStatusHistoriesRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new TicketStatusHistoryResourceApi();

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
    // 'OPEN' | 'UNDER_REVIEW' | 'APPROVED' | 'ASSIGNED' | 'IN_PROGRESS' | 'RESOLVED' | 'REJECTED' | 'CLOSED' (optional)
    oldStatusEquals: oldStatusEquals_example,
    // 'OPEN' | 'UNDER_REVIEW' | 'APPROVED' | 'ASSIGNED' | 'IN_PROGRESS' | 'RESOLVED' | 'REJECTED' | 'CLOSED' (optional)
    oldStatusNotEquals: oldStatusNotEquals_example,
    // boolean (optional)
    oldStatusSpecified: true,
    // Array<'OPEN' | 'UNDER_REVIEW' | 'APPROVED' | 'ASSIGNED' | 'IN_PROGRESS' | 'RESOLVED' | 'REJECTED' | 'CLOSED'> (optional)
    oldStatusIn: ...,
    // Array<'OPEN' | 'UNDER_REVIEW' | 'APPROVED' | 'ASSIGNED' | 'IN_PROGRESS' | 'RESOLVED' | 'REJECTED' | 'CLOSED'> (optional)
    oldStatusNotIn: ...,
    // 'OPEN' | 'UNDER_REVIEW' | 'APPROVED' | 'ASSIGNED' | 'IN_PROGRESS' | 'RESOLVED' | 'REJECTED' | 'CLOSED' (optional)
    newStatusEquals: newStatusEquals_example,
    // 'OPEN' | 'UNDER_REVIEW' | 'APPROVED' | 'ASSIGNED' | 'IN_PROGRESS' | 'RESOLVED' | 'REJECTED' | 'CLOSED' (optional)
    newStatusNotEquals: newStatusNotEquals_example,
    // boolean (optional)
    newStatusSpecified: true,
    // Array<'OPEN' | 'UNDER_REVIEW' | 'APPROVED' | 'ASSIGNED' | 'IN_PROGRESS' | 'RESOLVED' | 'REJECTED' | 'CLOSED'> (optional)
    newStatusIn: ...,
    // Array<'OPEN' | 'UNDER_REVIEW' | 'APPROVED' | 'ASSIGNED' | 'IN_PROGRESS' | 'RESOLVED' | 'REJECTED' | 'CLOSED'> (optional)
    newStatusNotIn: ...,
    // Date (optional)
    changedDateGreaterThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    changedDateLessThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    changedDateGreaterThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    changedDateLessThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    changedDateEquals: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    changedDateNotEquals: 2013-10-20T19:20:30+01:00,
    // boolean (optional)
    changedDateSpecified: true,
    // Array<Date> (optional)
    changedDateIn: ...,
    // Array<Date> (optional)
    changedDateNotIn: ...,
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
    changedByIdGreaterThan: 789,
    // number (optional)
    changedByIdLessThan: 789,
    // number (optional)
    changedByIdGreaterThanOrEqual: 789,
    // number (optional)
    changedByIdLessThanOrEqual: 789,
    // number (optional)
    changedByIdEquals: 789,
    // number (optional)
    changedByIdNotEquals: 789,
    // boolean (optional)
    changedByIdSpecified: true,
    // Array<number> (optional)
    changedByIdIn: ...,
    // Array<number> (optional)
    changedByIdNotIn: ...,
    // boolean (optional)
    distinct: true,
  } satisfies CountTicketStatusHistoriesRequest;

  try {
    const data = await api.countTicketStatusHistories(body);
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
| **oldStatusEquals** | `OPEN`, `UNDER_REVIEW`, `APPROVED`, `ASSIGNED`, `IN_PROGRESS`, `RESOLVED`, `REJECTED`, `CLOSED` |  | [Optional] [Defaults to `undefined`] [Enum: OPEN, UNDER_REVIEW, APPROVED, ASSIGNED, IN_PROGRESS, RESOLVED, REJECTED, CLOSED] |
| **oldStatusNotEquals** | `OPEN`, `UNDER_REVIEW`, `APPROVED`, `ASSIGNED`, `IN_PROGRESS`, `RESOLVED`, `REJECTED`, `CLOSED` |  | [Optional] [Defaults to `undefined`] [Enum: OPEN, UNDER_REVIEW, APPROVED, ASSIGNED, IN_PROGRESS, RESOLVED, REJECTED, CLOSED] |
| **oldStatusSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **oldStatusIn** | `OPEN`, `UNDER_REVIEW`, `APPROVED`, `ASSIGNED`, `IN_PROGRESS`, `RESOLVED`, `REJECTED`, `CLOSED` |  | [Optional] [Enum: OPEN, UNDER_REVIEW, APPROVED, ASSIGNED, IN_PROGRESS, RESOLVED, REJECTED, CLOSED] |
| **oldStatusNotIn** | `OPEN`, `UNDER_REVIEW`, `APPROVED`, `ASSIGNED`, `IN_PROGRESS`, `RESOLVED`, `REJECTED`, `CLOSED` |  | [Optional] [Enum: OPEN, UNDER_REVIEW, APPROVED, ASSIGNED, IN_PROGRESS, RESOLVED, REJECTED, CLOSED] |
| **newStatusEquals** | `OPEN`, `UNDER_REVIEW`, `APPROVED`, `ASSIGNED`, `IN_PROGRESS`, `RESOLVED`, `REJECTED`, `CLOSED` |  | [Optional] [Defaults to `undefined`] [Enum: OPEN, UNDER_REVIEW, APPROVED, ASSIGNED, IN_PROGRESS, RESOLVED, REJECTED, CLOSED] |
| **newStatusNotEquals** | `OPEN`, `UNDER_REVIEW`, `APPROVED`, `ASSIGNED`, `IN_PROGRESS`, `RESOLVED`, `REJECTED`, `CLOSED` |  | [Optional] [Defaults to `undefined`] [Enum: OPEN, UNDER_REVIEW, APPROVED, ASSIGNED, IN_PROGRESS, RESOLVED, REJECTED, CLOSED] |
| **newStatusSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **newStatusIn** | `OPEN`, `UNDER_REVIEW`, `APPROVED`, `ASSIGNED`, `IN_PROGRESS`, `RESOLVED`, `REJECTED`, `CLOSED` |  | [Optional] [Enum: OPEN, UNDER_REVIEW, APPROVED, ASSIGNED, IN_PROGRESS, RESOLVED, REJECTED, CLOSED] |
| **newStatusNotIn** | `OPEN`, `UNDER_REVIEW`, `APPROVED`, `ASSIGNED`, `IN_PROGRESS`, `RESOLVED`, `REJECTED`, `CLOSED` |  | [Optional] [Enum: OPEN, UNDER_REVIEW, APPROVED, ASSIGNED, IN_PROGRESS, RESOLVED, REJECTED, CLOSED] |
| **changedDateGreaterThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **changedDateLessThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **changedDateGreaterThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **changedDateLessThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **changedDateEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **changedDateNotEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **changedDateSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **changedDateIn** | `Array<Date>` |  | [Optional] |
| **changedDateNotIn** | `Array<Date>` |  | [Optional] |
| **ticketIdGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **ticketIdLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **ticketIdGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **ticketIdLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **ticketIdEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **ticketIdNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **ticketIdSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **ticketIdIn** | `Array<number>` |  | [Optional] |
| **ticketIdNotIn** | `Array<number>` |  | [Optional] |
| **changedByIdGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **changedByIdLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **changedByIdGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **changedByIdLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **changedByIdEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **changedByIdNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **changedByIdSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **changedByIdIn** | `Array<number>` |  | [Optional] |
| **changedByIdNotIn** | `Array<number>` |  | [Optional] |
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


## createTicketStatusHistory

> TicketStatusHistoryDTO createTicketStatusHistory(ticketStatusHistoryDTO)



### Example

```ts
import {
  Configuration,
  TicketStatusHistoryResourceApi,
} from '';
import type { CreateTicketStatusHistoryRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new TicketStatusHistoryResourceApi();

  const body = {
    // TicketStatusHistoryDTO
    ticketStatusHistoryDTO: ...,
  } satisfies CreateTicketStatusHistoryRequest;

  try {
    const data = await api.createTicketStatusHistory(body);
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
| **ticketStatusHistoryDTO** | [TicketStatusHistoryDTO](TicketStatusHistoryDTO.md) |  | |

### Return type

[**TicketStatusHistoryDTO**](TicketStatusHistoryDTO.md)

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


## deleteTicketStatusHistory

> deleteTicketStatusHistory(id)



### Example

```ts
import {
  Configuration,
  TicketStatusHistoryResourceApi,
} from '';
import type { DeleteTicketStatusHistoryRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new TicketStatusHistoryResourceApi();

  const body = {
    // number
    id: 789,
  } satisfies DeleteTicketStatusHistoryRequest;

  try {
    const data = await api.deleteTicketStatusHistory(body);
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


## getAllTicketStatusHistories

> Array&lt;TicketStatusHistoryDTO&gt; getAllTicketStatusHistories(idGreaterThan, idLessThan, idGreaterThanOrEqual, idLessThanOrEqual, idEquals, idNotEquals, idSpecified, idIn, idNotIn, oldStatusEquals, oldStatusNotEquals, oldStatusSpecified, oldStatusIn, oldStatusNotIn, newStatusEquals, newStatusNotEquals, newStatusSpecified, newStatusIn, newStatusNotIn, changedDateGreaterThan, changedDateLessThan, changedDateGreaterThanOrEqual, changedDateLessThanOrEqual, changedDateEquals, changedDateNotEquals, changedDateSpecified, changedDateIn, changedDateNotIn, ticketIdGreaterThan, ticketIdLessThan, ticketIdGreaterThanOrEqual, ticketIdLessThanOrEqual, ticketIdEquals, ticketIdNotEquals, ticketIdSpecified, ticketIdIn, ticketIdNotIn, changedByIdGreaterThan, changedByIdLessThan, changedByIdGreaterThanOrEqual, changedByIdLessThanOrEqual, changedByIdEquals, changedByIdNotEquals, changedByIdSpecified, changedByIdIn, changedByIdNotIn, distinct, page, size, sort)



### Example

```ts
import {
  Configuration,
  TicketStatusHistoryResourceApi,
} from '';
import type { GetAllTicketStatusHistoriesRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new TicketStatusHistoryResourceApi();

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
    // 'OPEN' | 'UNDER_REVIEW' | 'APPROVED' | 'ASSIGNED' | 'IN_PROGRESS' | 'RESOLVED' | 'REJECTED' | 'CLOSED' (optional)
    oldStatusEquals: oldStatusEquals_example,
    // 'OPEN' | 'UNDER_REVIEW' | 'APPROVED' | 'ASSIGNED' | 'IN_PROGRESS' | 'RESOLVED' | 'REJECTED' | 'CLOSED' (optional)
    oldStatusNotEquals: oldStatusNotEquals_example,
    // boolean (optional)
    oldStatusSpecified: true,
    // Array<'OPEN' | 'UNDER_REVIEW' | 'APPROVED' | 'ASSIGNED' | 'IN_PROGRESS' | 'RESOLVED' | 'REJECTED' | 'CLOSED'> (optional)
    oldStatusIn: ...,
    // Array<'OPEN' | 'UNDER_REVIEW' | 'APPROVED' | 'ASSIGNED' | 'IN_PROGRESS' | 'RESOLVED' | 'REJECTED' | 'CLOSED'> (optional)
    oldStatusNotIn: ...,
    // 'OPEN' | 'UNDER_REVIEW' | 'APPROVED' | 'ASSIGNED' | 'IN_PROGRESS' | 'RESOLVED' | 'REJECTED' | 'CLOSED' (optional)
    newStatusEquals: newStatusEquals_example,
    // 'OPEN' | 'UNDER_REVIEW' | 'APPROVED' | 'ASSIGNED' | 'IN_PROGRESS' | 'RESOLVED' | 'REJECTED' | 'CLOSED' (optional)
    newStatusNotEquals: newStatusNotEquals_example,
    // boolean (optional)
    newStatusSpecified: true,
    // Array<'OPEN' | 'UNDER_REVIEW' | 'APPROVED' | 'ASSIGNED' | 'IN_PROGRESS' | 'RESOLVED' | 'REJECTED' | 'CLOSED'> (optional)
    newStatusIn: ...,
    // Array<'OPEN' | 'UNDER_REVIEW' | 'APPROVED' | 'ASSIGNED' | 'IN_PROGRESS' | 'RESOLVED' | 'REJECTED' | 'CLOSED'> (optional)
    newStatusNotIn: ...,
    // Date (optional)
    changedDateGreaterThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    changedDateLessThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    changedDateGreaterThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    changedDateLessThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    changedDateEquals: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    changedDateNotEquals: 2013-10-20T19:20:30+01:00,
    // boolean (optional)
    changedDateSpecified: true,
    // Array<Date> (optional)
    changedDateIn: ...,
    // Array<Date> (optional)
    changedDateNotIn: ...,
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
    changedByIdGreaterThan: 789,
    // number (optional)
    changedByIdLessThan: 789,
    // number (optional)
    changedByIdGreaterThanOrEqual: 789,
    // number (optional)
    changedByIdLessThanOrEqual: 789,
    // number (optional)
    changedByIdEquals: 789,
    // number (optional)
    changedByIdNotEquals: 789,
    // boolean (optional)
    changedByIdSpecified: true,
    // Array<number> (optional)
    changedByIdIn: ...,
    // Array<number> (optional)
    changedByIdNotIn: ...,
    // boolean (optional)
    distinct: true,
    // number | Zero-based page index (0..N) (optional)
    page: 56,
    // number | The size of the page to be returned (optional)
    size: 56,
    // Array<string> | Sorting criteria in the format: property,(asc|desc). Default sort order is ascending. Multiple sort criteria are supported. (optional)
    sort: ...,
  } satisfies GetAllTicketStatusHistoriesRequest;

  try {
    const data = await api.getAllTicketStatusHistories(body);
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
| **oldStatusEquals** | `OPEN`, `UNDER_REVIEW`, `APPROVED`, `ASSIGNED`, `IN_PROGRESS`, `RESOLVED`, `REJECTED`, `CLOSED` |  | [Optional] [Defaults to `undefined`] [Enum: OPEN, UNDER_REVIEW, APPROVED, ASSIGNED, IN_PROGRESS, RESOLVED, REJECTED, CLOSED] |
| **oldStatusNotEquals** | `OPEN`, `UNDER_REVIEW`, `APPROVED`, `ASSIGNED`, `IN_PROGRESS`, `RESOLVED`, `REJECTED`, `CLOSED` |  | [Optional] [Defaults to `undefined`] [Enum: OPEN, UNDER_REVIEW, APPROVED, ASSIGNED, IN_PROGRESS, RESOLVED, REJECTED, CLOSED] |
| **oldStatusSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **oldStatusIn** | `OPEN`, `UNDER_REVIEW`, `APPROVED`, `ASSIGNED`, `IN_PROGRESS`, `RESOLVED`, `REJECTED`, `CLOSED` |  | [Optional] [Enum: OPEN, UNDER_REVIEW, APPROVED, ASSIGNED, IN_PROGRESS, RESOLVED, REJECTED, CLOSED] |
| **oldStatusNotIn** | `OPEN`, `UNDER_REVIEW`, `APPROVED`, `ASSIGNED`, `IN_PROGRESS`, `RESOLVED`, `REJECTED`, `CLOSED` |  | [Optional] [Enum: OPEN, UNDER_REVIEW, APPROVED, ASSIGNED, IN_PROGRESS, RESOLVED, REJECTED, CLOSED] |
| **newStatusEquals** | `OPEN`, `UNDER_REVIEW`, `APPROVED`, `ASSIGNED`, `IN_PROGRESS`, `RESOLVED`, `REJECTED`, `CLOSED` |  | [Optional] [Defaults to `undefined`] [Enum: OPEN, UNDER_REVIEW, APPROVED, ASSIGNED, IN_PROGRESS, RESOLVED, REJECTED, CLOSED] |
| **newStatusNotEquals** | `OPEN`, `UNDER_REVIEW`, `APPROVED`, `ASSIGNED`, `IN_PROGRESS`, `RESOLVED`, `REJECTED`, `CLOSED` |  | [Optional] [Defaults to `undefined`] [Enum: OPEN, UNDER_REVIEW, APPROVED, ASSIGNED, IN_PROGRESS, RESOLVED, REJECTED, CLOSED] |
| **newStatusSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **newStatusIn** | `OPEN`, `UNDER_REVIEW`, `APPROVED`, `ASSIGNED`, `IN_PROGRESS`, `RESOLVED`, `REJECTED`, `CLOSED` |  | [Optional] [Enum: OPEN, UNDER_REVIEW, APPROVED, ASSIGNED, IN_PROGRESS, RESOLVED, REJECTED, CLOSED] |
| **newStatusNotIn** | `OPEN`, `UNDER_REVIEW`, `APPROVED`, `ASSIGNED`, `IN_PROGRESS`, `RESOLVED`, `REJECTED`, `CLOSED` |  | [Optional] [Enum: OPEN, UNDER_REVIEW, APPROVED, ASSIGNED, IN_PROGRESS, RESOLVED, REJECTED, CLOSED] |
| **changedDateGreaterThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **changedDateLessThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **changedDateGreaterThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **changedDateLessThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **changedDateEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **changedDateNotEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **changedDateSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **changedDateIn** | `Array<Date>` |  | [Optional] |
| **changedDateNotIn** | `Array<Date>` |  | [Optional] |
| **ticketIdGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **ticketIdLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **ticketIdGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **ticketIdLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **ticketIdEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **ticketIdNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **ticketIdSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **ticketIdIn** | `Array<number>` |  | [Optional] |
| **ticketIdNotIn** | `Array<number>` |  | [Optional] |
| **changedByIdGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **changedByIdLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **changedByIdGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **changedByIdLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **changedByIdEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **changedByIdNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **changedByIdSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **changedByIdIn** | `Array<number>` |  | [Optional] |
| **changedByIdNotIn** | `Array<number>` |  | [Optional] |
| **distinct** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **page** | `number` | Zero-based page index (0..N) | [Optional] [Defaults to `0`] |
| **size** | `number` | The size of the page to be returned | [Optional] [Defaults to `20`] |
| **sort** | `Array<string>` | Sorting criteria in the format: property,(asc|desc). Default sort order is ascending. Multiple sort criteria are supported. | [Optional] |

### Return type

[**Array&lt;TicketStatusHistoryDTO&gt;**](TicketStatusHistoryDTO.md)

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


## getTicketStatusHistory

> TicketStatusHistoryDTO getTicketStatusHistory(id)



### Example

```ts
import {
  Configuration,
  TicketStatusHistoryResourceApi,
} from '';
import type { GetTicketStatusHistoryRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new TicketStatusHistoryResourceApi();

  const body = {
    // number
    id: 789,
  } satisfies GetTicketStatusHistoryRequest;

  try {
    const data = await api.getTicketStatusHistory(body);
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

[**TicketStatusHistoryDTO**](TicketStatusHistoryDTO.md)

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


## partialUpdateTicketStatusHistory

> TicketStatusHistoryDTO partialUpdateTicketStatusHistory(id, ticketStatusHistoryDTO)



### Example

```ts
import {
  Configuration,
  TicketStatusHistoryResourceApi,
} from '';
import type { PartialUpdateTicketStatusHistoryRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new TicketStatusHistoryResourceApi();

  const body = {
    // number
    id: 789,
    // TicketStatusHistoryDTO
    ticketStatusHistoryDTO: ...,
  } satisfies PartialUpdateTicketStatusHistoryRequest;

  try {
    const data = await api.partialUpdateTicketStatusHistory(body);
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
| **ticketStatusHistoryDTO** | [TicketStatusHistoryDTO](TicketStatusHistoryDTO.md) |  | |

### Return type

[**TicketStatusHistoryDTO**](TicketStatusHistoryDTO.md)

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


## updateTicketStatusHistory

> TicketStatusHistoryDTO updateTicketStatusHistory(id, ticketStatusHistoryDTO)



### Example

```ts
import {
  Configuration,
  TicketStatusHistoryResourceApi,
} from '';
import type { UpdateTicketStatusHistoryRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new TicketStatusHistoryResourceApi();

  const body = {
    // number
    id: 789,
    // TicketStatusHistoryDTO
    ticketStatusHistoryDTO: ...,
  } satisfies UpdateTicketStatusHistoryRequest;

  try {
    const data = await api.updateTicketStatusHistory(body);
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
| **ticketStatusHistoryDTO** | [TicketStatusHistoryDTO](TicketStatusHistoryDTO.md) |  | |

### Return type

[**TicketStatusHistoryDTO**](TicketStatusHistoryDTO.md)

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

