
# WorkPlanDTO


## Properties

Name | Type
------------ | -------------
`id` | number
`estimatedCost` | number
`startedDate` | Date
`expectedCompletionDate` | Date
`actualCompletionDate` | Date
`completionPercentage` | number
`status` | string
`remarks` | string
`deleted` | boolean
`deletedDate` | Date
`ticket` | [TicketDTO](TicketDTO.md)
`department` | [DepartmentDTO](DepartmentDTO.md)

## Example

```typescript
import type { WorkPlanDTO } from ''

// TODO: Update the object below with actual values
const example = {
  "id": null,
  "estimatedCost": null,
  "startedDate": null,
  "expectedCompletionDate": null,
  "actualCompletionDate": null,
  "completionPercentage": null,
  "status": null,
  "remarks": null,
  "deleted": null,
  "deletedDate": null,
  "ticket": null,
  "department": null,
} satisfies WorkPlanDTO

console.log(example)

// Convert the instance to a JSON string
const exampleJSON: string = JSON.stringify(example)
console.log(exampleJSON)

// Parse the JSON string back to an object
const exampleParsed = JSON.parse(exampleJSON) as WorkPlanDTO
console.log(exampleParsed)
```

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


