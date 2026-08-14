
# LocationDTO


## Properties

Name | Type
------------ | -------------
`id` | number
`addressText` | string
`landmark` | string
`latitude` | number
`longitude` | number
`ward` | [WardDTO](WardDTO.md)

## Example

```typescript
import type { LocationDTO } from ''

// TODO: Update the object below with actual values
const example = {
  "id": null,
  "addressText": null,
  "landmark": null,
  "latitude": null,
  "longitude": null,
  "ward": null,
} satisfies LocationDTO

console.log(example)

// Convert the instance to a JSON string
const exampleJSON: string = JSON.stringify(example)
console.log(exampleJSON)

// Parse the JSON string back to an object
const exampleParsed = JSON.parse(exampleJSON) as LocationDTO
console.log(exampleParsed)
```

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


