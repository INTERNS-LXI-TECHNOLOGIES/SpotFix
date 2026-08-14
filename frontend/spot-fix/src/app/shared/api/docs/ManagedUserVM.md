
# ManagedUserVM


## Properties

Name | Type
------------ | -------------
`id` | number
`login` | string
`firstName` | string
`lastName` | string
`email` | string
`imageUrl` | string
`activated` | boolean
`langKey` | string
`createdBy` | string
`createdDate` | Date
`lastModifiedBy` | string
`lastModifiedDate` | Date
`authorities` | Set&lt;string&gt;
`password` | string

## Example

```typescript
import type { ManagedUserVM } from ''

// TODO: Update the object below with actual values
const example = {
  "id": null,
  "login": null,
  "firstName": null,
  "lastName": null,
  "email": null,
  "imageUrl": null,
  "activated": null,
  "langKey": null,
  "createdBy": null,
  "createdDate": null,
  "lastModifiedBy": null,
  "lastModifiedDate": null,
  "authorities": null,
  "password": null,
} satisfies ManagedUserVM

console.log(example)

// Convert the instance to a JSON string
const exampleJSON: string = JSON.stringify(example)
console.log(exampleJSON)

// Parse the JSON string back to an object
const exampleParsed = JSON.parse(exampleJSON) as ManagedUserVM
console.log(exampleParsed)
```

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


