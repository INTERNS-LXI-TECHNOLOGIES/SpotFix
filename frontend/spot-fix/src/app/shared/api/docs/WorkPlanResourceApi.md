# WorkPlanResourceApi

All URIs are relative to *http://localhost:8081*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**countWorkPlans**](WorkPlanResourceApi.md#countworkplans) | **GET** /api/work-plans/count |  |
| [**createWorkPlan**](WorkPlanResourceApi.md#createworkplan) | **POST** /api/work-plans |  |
| [**deleteWorkPlan**](WorkPlanResourceApi.md#deleteworkplan) | **DELETE** /api/work-plans/{id} |  |
| [**getAllWorkPlans**](WorkPlanResourceApi.md#getallworkplans) | **GET** /api/work-plans |  |
| [**getWorkPlan**](WorkPlanResourceApi.md#getworkplan) | **GET** /api/work-plans/{id} |  |
| [**partialUpdateWorkPlan**](WorkPlanResourceApi.md#partialupdateworkplan) | **PATCH** /api/work-plans/{id} |  |
| [**updateWorkPlan**](WorkPlanResourceApi.md#updateworkplan) | **PUT** /api/work-plans/{id} |  |



## countWorkPlans

> number countWorkPlans(idGreaterThan, idLessThan, idGreaterThanOrEqual, idLessThanOrEqual, idEquals, idNotEquals, idSpecified, idIn, idNotIn, estimatedCostGreaterThan, estimatedCostLessThan, estimatedCostGreaterThanOrEqual, estimatedCostLessThanOrEqual, estimatedCostEquals, estimatedCostNotEquals, estimatedCostSpecified, estimatedCostIn, estimatedCostNotIn, startedDateGreaterThan, startedDateLessThan, startedDateGreaterThanOrEqual, startedDateLessThanOrEqual, startedDateEquals, startedDateNotEquals, startedDateSpecified, startedDateIn, startedDateNotIn, expectedCompletionDateGreaterThan, expectedCompletionDateLessThan, expectedCompletionDateGreaterThanOrEqual, expectedCompletionDateLessThanOrEqual, expectedCompletionDateEquals, expectedCompletionDateNotEquals, expectedCompletionDateSpecified, expectedCompletionDateIn, expectedCompletionDateNotIn, actualCompletionDateGreaterThan, actualCompletionDateLessThan, actualCompletionDateGreaterThanOrEqual, actualCompletionDateLessThanOrEqual, actualCompletionDateEquals, actualCompletionDateNotEquals, actualCompletionDateSpecified, actualCompletionDateIn, actualCompletionDateNotIn, completionPercentageGreaterThan, completionPercentageLessThan, completionPercentageGreaterThanOrEqual, completionPercentageLessThanOrEqual, completionPercentageEquals, completionPercentageNotEquals, completionPercentageSpecified, completionPercentageIn, completionPercentageNotIn, statusEquals, statusNotEquals, statusSpecified, statusIn, statusNotIn, deletedEquals, deletedNotEquals, deletedSpecified, deletedIn, deletedNotIn, deletedDateGreaterThan, deletedDateLessThan, deletedDateGreaterThanOrEqual, deletedDateLessThanOrEqual, deletedDateEquals, deletedDateNotEquals, deletedDateSpecified, deletedDateIn, deletedDateNotIn, ticketIdGreaterThan, ticketIdLessThan, ticketIdGreaterThanOrEqual, ticketIdLessThanOrEqual, ticketIdEquals, ticketIdNotEquals, ticketIdSpecified, ticketIdIn, ticketIdNotIn, departmentIdGreaterThan, departmentIdLessThan, departmentIdGreaterThanOrEqual, departmentIdLessThanOrEqual, departmentIdEquals, departmentIdNotEquals, departmentIdSpecified, departmentIdIn, departmentIdNotIn, distinct)



### Example

```ts
import {
  Configuration,
  WorkPlanResourceApi,
} from '';
import type { CountWorkPlansRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new WorkPlanResourceApi();

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
    // number (optional)
    estimatedCostGreaterThan: 8.14,
    // number (optional)
    estimatedCostLessThan: 8.14,
    // number (optional)
    estimatedCostGreaterThanOrEqual: 8.14,
    // number (optional)
    estimatedCostLessThanOrEqual: 8.14,
    // number (optional)
    estimatedCostEquals: 8.14,
    // number (optional)
    estimatedCostNotEquals: 8.14,
    // boolean (optional)
    estimatedCostSpecified: true,
    // Array<number> (optional)
    estimatedCostIn: ...,
    // Array<number> (optional)
    estimatedCostNotIn: ...,
    // Date (optional)
    startedDateGreaterThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    startedDateLessThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    startedDateGreaterThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    startedDateLessThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    startedDateEquals: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    startedDateNotEquals: 2013-10-20T19:20:30+01:00,
    // boolean (optional)
    startedDateSpecified: true,
    // Array<Date> (optional)
    startedDateIn: ...,
    // Array<Date> (optional)
    startedDateNotIn: ...,
    // Date (optional)
    expectedCompletionDateGreaterThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    expectedCompletionDateLessThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    expectedCompletionDateGreaterThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    expectedCompletionDateLessThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    expectedCompletionDateEquals: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    expectedCompletionDateNotEquals: 2013-10-20T19:20:30+01:00,
    // boolean (optional)
    expectedCompletionDateSpecified: true,
    // Array<Date> (optional)
    expectedCompletionDateIn: ...,
    // Array<Date> (optional)
    expectedCompletionDateNotIn: ...,
    // Date (optional)
    actualCompletionDateGreaterThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    actualCompletionDateLessThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    actualCompletionDateGreaterThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    actualCompletionDateLessThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    actualCompletionDateEquals: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    actualCompletionDateNotEquals: 2013-10-20T19:20:30+01:00,
    // boolean (optional)
    actualCompletionDateSpecified: true,
    // Array<Date> (optional)
    actualCompletionDateIn: ...,
    // Array<Date> (optional)
    actualCompletionDateNotIn: ...,
    // number (optional)
    completionPercentageGreaterThan: 56,
    // number (optional)
    completionPercentageLessThan: 56,
    // number (optional)
    completionPercentageGreaterThanOrEqual: 56,
    // number (optional)
    completionPercentageLessThanOrEqual: 56,
    // number (optional)
    completionPercentageEquals: 56,
    // number (optional)
    completionPercentageNotEquals: 56,
    // boolean (optional)
    completionPercentageSpecified: true,
    // Array<number> (optional)
    completionPercentageIn: ...,
    // Array<number> (optional)
    completionPercentageNotIn: ...,
    // 'PLANNED' | 'ASSIGNED' | 'ACTIVE' | 'ON_HOLD' | 'DELAYED' | 'COMPLETED' | 'CANCELLED' (optional)
    statusEquals: statusEquals_example,
    // 'PLANNED' | 'ASSIGNED' | 'ACTIVE' | 'ON_HOLD' | 'DELAYED' | 'COMPLETED' | 'CANCELLED' (optional)
    statusNotEquals: statusNotEquals_example,
    // boolean (optional)
    statusSpecified: true,
    // Array<'PLANNED' | 'ASSIGNED' | 'ACTIVE' | 'ON_HOLD' | 'DELAYED' | 'COMPLETED' | 'CANCELLED'> (optional)
    statusIn: ...,
    // Array<'PLANNED' | 'ASSIGNED' | 'ACTIVE' | 'ON_HOLD' | 'DELAYED' | 'COMPLETED' | 'CANCELLED'> (optional)
    statusNotIn: ...,
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
    departmentIdGreaterThan: 789,
    // number (optional)
    departmentIdLessThan: 789,
    // number (optional)
    departmentIdGreaterThanOrEqual: 789,
    // number (optional)
    departmentIdLessThanOrEqual: 789,
    // number (optional)
    departmentIdEquals: 789,
    // number (optional)
    departmentIdNotEquals: 789,
    // boolean (optional)
    departmentIdSpecified: true,
    // Array<number> (optional)
    departmentIdIn: ...,
    // Array<number> (optional)
    departmentIdNotIn: ...,
    // boolean (optional)
    distinct: true,
  } satisfies CountWorkPlansRequest;

  try {
    const data = await api.countWorkPlans(body);
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
| **estimatedCostGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **estimatedCostLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **estimatedCostGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **estimatedCostLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **estimatedCostEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **estimatedCostNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **estimatedCostSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **estimatedCostIn** | `Array<number>` |  | [Optional] |
| **estimatedCostNotIn** | `Array<number>` |  | [Optional] |
| **startedDateGreaterThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **startedDateLessThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **startedDateGreaterThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **startedDateLessThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **startedDateEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **startedDateNotEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **startedDateSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **startedDateIn** | `Array<Date>` |  | [Optional] |
| **startedDateNotIn** | `Array<Date>` |  | [Optional] |
| **expectedCompletionDateGreaterThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **expectedCompletionDateLessThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **expectedCompletionDateGreaterThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **expectedCompletionDateLessThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **expectedCompletionDateEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **expectedCompletionDateNotEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **expectedCompletionDateSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **expectedCompletionDateIn** | `Array<Date>` |  | [Optional] |
| **expectedCompletionDateNotIn** | `Array<Date>` |  | [Optional] |
| **actualCompletionDateGreaterThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **actualCompletionDateLessThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **actualCompletionDateGreaterThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **actualCompletionDateLessThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **actualCompletionDateEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **actualCompletionDateNotEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **actualCompletionDateSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **actualCompletionDateIn** | `Array<Date>` |  | [Optional] |
| **actualCompletionDateNotIn** | `Array<Date>` |  | [Optional] |
| **completionPercentageGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **completionPercentageLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **completionPercentageGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **completionPercentageLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **completionPercentageEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **completionPercentageNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **completionPercentageSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **completionPercentageIn** | `Array<number>` |  | [Optional] |
| **completionPercentageNotIn** | `Array<number>` |  | [Optional] |
| **statusEquals** | `PLANNED`, `ASSIGNED`, `ACTIVE`, `ON_HOLD`, `DELAYED`, `COMPLETED`, `CANCELLED` |  | [Optional] [Defaults to `undefined`] [Enum: PLANNED, ASSIGNED, ACTIVE, ON_HOLD, DELAYED, COMPLETED, CANCELLED] |
| **statusNotEquals** | `PLANNED`, `ASSIGNED`, `ACTIVE`, `ON_HOLD`, `DELAYED`, `COMPLETED`, `CANCELLED` |  | [Optional] [Defaults to `undefined`] [Enum: PLANNED, ASSIGNED, ACTIVE, ON_HOLD, DELAYED, COMPLETED, CANCELLED] |
| **statusSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **statusIn** | `PLANNED`, `ASSIGNED`, `ACTIVE`, `ON_HOLD`, `DELAYED`, `COMPLETED`, `CANCELLED` |  | [Optional] [Enum: PLANNED, ASSIGNED, ACTIVE, ON_HOLD, DELAYED, COMPLETED, CANCELLED] |
| **statusNotIn** | `PLANNED`, `ASSIGNED`, `ACTIVE`, `ON_HOLD`, `DELAYED`, `COMPLETED`, `CANCELLED` |  | [Optional] [Enum: PLANNED, ASSIGNED, ACTIVE, ON_HOLD, DELAYED, COMPLETED, CANCELLED] |
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
| **departmentIdGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **departmentIdLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **departmentIdGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **departmentIdLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **departmentIdEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **departmentIdNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **departmentIdSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **departmentIdIn** | `Array<number>` |  | [Optional] |
| **departmentIdNotIn** | `Array<number>` |  | [Optional] |
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


## createWorkPlan

> WorkPlanDTO createWorkPlan(workPlanDTO)



### Example

```ts
import {
  Configuration,
  WorkPlanResourceApi,
} from '';
import type { CreateWorkPlanRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new WorkPlanResourceApi();

  const body = {
    // WorkPlanDTO
    workPlanDTO: ...,
  } satisfies CreateWorkPlanRequest;

  try {
    const data = await api.createWorkPlan(body);
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
| **workPlanDTO** | [WorkPlanDTO](WorkPlanDTO.md) |  | |

### Return type

[**WorkPlanDTO**](WorkPlanDTO.md)

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


## deleteWorkPlan

> deleteWorkPlan(id)



### Example

```ts
import {
  Configuration,
  WorkPlanResourceApi,
} from '';
import type { DeleteWorkPlanRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new WorkPlanResourceApi();

  const body = {
    // number
    id: 789,
  } satisfies DeleteWorkPlanRequest;

  try {
    const data = await api.deleteWorkPlan(body);
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


## getAllWorkPlans

> Array&lt;WorkPlanDTO&gt; getAllWorkPlans(idGreaterThan, idLessThan, idGreaterThanOrEqual, idLessThanOrEqual, idEquals, idNotEquals, idSpecified, idIn, idNotIn, estimatedCostGreaterThan, estimatedCostLessThan, estimatedCostGreaterThanOrEqual, estimatedCostLessThanOrEqual, estimatedCostEquals, estimatedCostNotEquals, estimatedCostSpecified, estimatedCostIn, estimatedCostNotIn, startedDateGreaterThan, startedDateLessThan, startedDateGreaterThanOrEqual, startedDateLessThanOrEqual, startedDateEquals, startedDateNotEquals, startedDateSpecified, startedDateIn, startedDateNotIn, expectedCompletionDateGreaterThan, expectedCompletionDateLessThan, expectedCompletionDateGreaterThanOrEqual, expectedCompletionDateLessThanOrEqual, expectedCompletionDateEquals, expectedCompletionDateNotEquals, expectedCompletionDateSpecified, expectedCompletionDateIn, expectedCompletionDateNotIn, actualCompletionDateGreaterThan, actualCompletionDateLessThan, actualCompletionDateGreaterThanOrEqual, actualCompletionDateLessThanOrEqual, actualCompletionDateEquals, actualCompletionDateNotEquals, actualCompletionDateSpecified, actualCompletionDateIn, actualCompletionDateNotIn, completionPercentageGreaterThan, completionPercentageLessThan, completionPercentageGreaterThanOrEqual, completionPercentageLessThanOrEqual, completionPercentageEquals, completionPercentageNotEquals, completionPercentageSpecified, completionPercentageIn, completionPercentageNotIn, statusEquals, statusNotEquals, statusSpecified, statusIn, statusNotIn, deletedEquals, deletedNotEquals, deletedSpecified, deletedIn, deletedNotIn, deletedDateGreaterThan, deletedDateLessThan, deletedDateGreaterThanOrEqual, deletedDateLessThanOrEqual, deletedDateEquals, deletedDateNotEquals, deletedDateSpecified, deletedDateIn, deletedDateNotIn, ticketIdGreaterThan, ticketIdLessThan, ticketIdGreaterThanOrEqual, ticketIdLessThanOrEqual, ticketIdEquals, ticketIdNotEquals, ticketIdSpecified, ticketIdIn, ticketIdNotIn, departmentIdGreaterThan, departmentIdLessThan, departmentIdGreaterThanOrEqual, departmentIdLessThanOrEqual, departmentIdEquals, departmentIdNotEquals, departmentIdSpecified, departmentIdIn, departmentIdNotIn, distinct, page, size, sort)



### Example

```ts
import {
  Configuration,
  WorkPlanResourceApi,
} from '';
import type { GetAllWorkPlansRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new WorkPlanResourceApi();

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
    // number (optional)
    estimatedCostGreaterThan: 8.14,
    // number (optional)
    estimatedCostLessThan: 8.14,
    // number (optional)
    estimatedCostGreaterThanOrEqual: 8.14,
    // number (optional)
    estimatedCostLessThanOrEqual: 8.14,
    // number (optional)
    estimatedCostEquals: 8.14,
    // number (optional)
    estimatedCostNotEquals: 8.14,
    // boolean (optional)
    estimatedCostSpecified: true,
    // Array<number> (optional)
    estimatedCostIn: ...,
    // Array<number> (optional)
    estimatedCostNotIn: ...,
    // Date (optional)
    startedDateGreaterThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    startedDateLessThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    startedDateGreaterThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    startedDateLessThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    startedDateEquals: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    startedDateNotEquals: 2013-10-20T19:20:30+01:00,
    // boolean (optional)
    startedDateSpecified: true,
    // Array<Date> (optional)
    startedDateIn: ...,
    // Array<Date> (optional)
    startedDateNotIn: ...,
    // Date (optional)
    expectedCompletionDateGreaterThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    expectedCompletionDateLessThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    expectedCompletionDateGreaterThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    expectedCompletionDateLessThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    expectedCompletionDateEquals: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    expectedCompletionDateNotEquals: 2013-10-20T19:20:30+01:00,
    // boolean (optional)
    expectedCompletionDateSpecified: true,
    // Array<Date> (optional)
    expectedCompletionDateIn: ...,
    // Array<Date> (optional)
    expectedCompletionDateNotIn: ...,
    // Date (optional)
    actualCompletionDateGreaterThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    actualCompletionDateLessThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    actualCompletionDateGreaterThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    actualCompletionDateLessThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    actualCompletionDateEquals: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    actualCompletionDateNotEquals: 2013-10-20T19:20:30+01:00,
    // boolean (optional)
    actualCompletionDateSpecified: true,
    // Array<Date> (optional)
    actualCompletionDateIn: ...,
    // Array<Date> (optional)
    actualCompletionDateNotIn: ...,
    // number (optional)
    completionPercentageGreaterThan: 56,
    // number (optional)
    completionPercentageLessThan: 56,
    // number (optional)
    completionPercentageGreaterThanOrEqual: 56,
    // number (optional)
    completionPercentageLessThanOrEqual: 56,
    // number (optional)
    completionPercentageEquals: 56,
    // number (optional)
    completionPercentageNotEquals: 56,
    // boolean (optional)
    completionPercentageSpecified: true,
    // Array<number> (optional)
    completionPercentageIn: ...,
    // Array<number> (optional)
    completionPercentageNotIn: ...,
    // 'PLANNED' | 'ASSIGNED' | 'ACTIVE' | 'ON_HOLD' | 'DELAYED' | 'COMPLETED' | 'CANCELLED' (optional)
    statusEquals: statusEquals_example,
    // 'PLANNED' | 'ASSIGNED' | 'ACTIVE' | 'ON_HOLD' | 'DELAYED' | 'COMPLETED' | 'CANCELLED' (optional)
    statusNotEquals: statusNotEquals_example,
    // boolean (optional)
    statusSpecified: true,
    // Array<'PLANNED' | 'ASSIGNED' | 'ACTIVE' | 'ON_HOLD' | 'DELAYED' | 'COMPLETED' | 'CANCELLED'> (optional)
    statusIn: ...,
    // Array<'PLANNED' | 'ASSIGNED' | 'ACTIVE' | 'ON_HOLD' | 'DELAYED' | 'COMPLETED' | 'CANCELLED'> (optional)
    statusNotIn: ...,
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
    departmentIdGreaterThan: 789,
    // number (optional)
    departmentIdLessThan: 789,
    // number (optional)
    departmentIdGreaterThanOrEqual: 789,
    // number (optional)
    departmentIdLessThanOrEqual: 789,
    // number (optional)
    departmentIdEquals: 789,
    // number (optional)
    departmentIdNotEquals: 789,
    // boolean (optional)
    departmentIdSpecified: true,
    // Array<number> (optional)
    departmentIdIn: ...,
    // Array<number> (optional)
    departmentIdNotIn: ...,
    // boolean (optional)
    distinct: true,
    // number | Zero-based page index (0..N) (optional)
    page: 56,
    // number | The size of the page to be returned (optional)
    size: 56,
    // Array<string> | Sorting criteria in the format: property,(asc|desc). Default sort order is ascending. Multiple sort criteria are supported. (optional)
    sort: ...,
  } satisfies GetAllWorkPlansRequest;

  try {
    const data = await api.getAllWorkPlans(body);
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
| **estimatedCostGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **estimatedCostLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **estimatedCostGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **estimatedCostLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **estimatedCostEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **estimatedCostNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **estimatedCostSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **estimatedCostIn** | `Array<number>` |  | [Optional] |
| **estimatedCostNotIn** | `Array<number>` |  | [Optional] |
| **startedDateGreaterThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **startedDateLessThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **startedDateGreaterThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **startedDateLessThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **startedDateEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **startedDateNotEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **startedDateSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **startedDateIn** | `Array<Date>` |  | [Optional] |
| **startedDateNotIn** | `Array<Date>` |  | [Optional] |
| **expectedCompletionDateGreaterThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **expectedCompletionDateLessThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **expectedCompletionDateGreaterThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **expectedCompletionDateLessThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **expectedCompletionDateEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **expectedCompletionDateNotEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **expectedCompletionDateSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **expectedCompletionDateIn** | `Array<Date>` |  | [Optional] |
| **expectedCompletionDateNotIn** | `Array<Date>` |  | [Optional] |
| **actualCompletionDateGreaterThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **actualCompletionDateLessThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **actualCompletionDateGreaterThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **actualCompletionDateLessThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **actualCompletionDateEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **actualCompletionDateNotEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **actualCompletionDateSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **actualCompletionDateIn** | `Array<Date>` |  | [Optional] |
| **actualCompletionDateNotIn** | `Array<Date>` |  | [Optional] |
| **completionPercentageGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **completionPercentageLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **completionPercentageGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **completionPercentageLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **completionPercentageEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **completionPercentageNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **completionPercentageSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **completionPercentageIn** | `Array<number>` |  | [Optional] |
| **completionPercentageNotIn** | `Array<number>` |  | [Optional] |
| **statusEquals** | `PLANNED`, `ASSIGNED`, `ACTIVE`, `ON_HOLD`, `DELAYED`, `COMPLETED`, `CANCELLED` |  | [Optional] [Defaults to `undefined`] [Enum: PLANNED, ASSIGNED, ACTIVE, ON_HOLD, DELAYED, COMPLETED, CANCELLED] |
| **statusNotEquals** | `PLANNED`, `ASSIGNED`, `ACTIVE`, `ON_HOLD`, `DELAYED`, `COMPLETED`, `CANCELLED` |  | [Optional] [Defaults to `undefined`] [Enum: PLANNED, ASSIGNED, ACTIVE, ON_HOLD, DELAYED, COMPLETED, CANCELLED] |
| **statusSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **statusIn** | `PLANNED`, `ASSIGNED`, `ACTIVE`, `ON_HOLD`, `DELAYED`, `COMPLETED`, `CANCELLED` |  | [Optional] [Enum: PLANNED, ASSIGNED, ACTIVE, ON_HOLD, DELAYED, COMPLETED, CANCELLED] |
| **statusNotIn** | `PLANNED`, `ASSIGNED`, `ACTIVE`, `ON_HOLD`, `DELAYED`, `COMPLETED`, `CANCELLED` |  | [Optional] [Enum: PLANNED, ASSIGNED, ACTIVE, ON_HOLD, DELAYED, COMPLETED, CANCELLED] |
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
| **departmentIdGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **departmentIdLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **departmentIdGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **departmentIdLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **departmentIdEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **departmentIdNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **departmentIdSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **departmentIdIn** | `Array<number>` |  | [Optional] |
| **departmentIdNotIn** | `Array<number>` |  | [Optional] |
| **distinct** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **page** | `number` | Zero-based page index (0..N) | [Optional] [Defaults to `0`] |
| **size** | `number` | The size of the page to be returned | [Optional] [Defaults to `20`] |
| **sort** | `Array<string>` | Sorting criteria in the format: property,(asc|desc). Default sort order is ascending. Multiple sort criteria are supported. | [Optional] |

### Return type

[**Array&lt;WorkPlanDTO&gt;**](WorkPlanDTO.md)

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


## getWorkPlan

> WorkPlanDTO getWorkPlan(id)



### Example

```ts
import {
  Configuration,
  WorkPlanResourceApi,
} from '';
import type { GetWorkPlanRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new WorkPlanResourceApi();

  const body = {
    // number
    id: 789,
  } satisfies GetWorkPlanRequest;

  try {
    const data = await api.getWorkPlan(body);
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

[**WorkPlanDTO**](WorkPlanDTO.md)

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


## partialUpdateWorkPlan

> WorkPlanDTO partialUpdateWorkPlan(id, workPlanDTO)



### Example

```ts
import {
  Configuration,
  WorkPlanResourceApi,
} from '';
import type { PartialUpdateWorkPlanRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new WorkPlanResourceApi();

  const body = {
    // number
    id: 789,
    // WorkPlanDTO
    workPlanDTO: ...,
  } satisfies PartialUpdateWorkPlanRequest;

  try {
    const data = await api.partialUpdateWorkPlan(body);
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
| **workPlanDTO** | [WorkPlanDTO](WorkPlanDTO.md) |  | |

### Return type

[**WorkPlanDTO**](WorkPlanDTO.md)

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


## updateWorkPlan

> WorkPlanDTO updateWorkPlan(id, workPlanDTO)



### Example

```ts
import {
  Configuration,
  WorkPlanResourceApi,
} from '';
import type { UpdateWorkPlanRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new WorkPlanResourceApi();

  const body = {
    // number
    id: 789,
    // WorkPlanDTO
    workPlanDTO: ...,
  } satisfies UpdateWorkPlanRequest;

  try {
    const data = await api.updateWorkPlan(body);
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
| **workPlanDTO** | [WorkPlanDTO](WorkPlanDTO.md) |  | |

### Return type

[**WorkPlanDTO**](WorkPlanDTO.md)

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

