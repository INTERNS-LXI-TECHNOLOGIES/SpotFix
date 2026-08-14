
# UserProfileDTO


## Properties

Name | Type
------------ | -------------
`id` | number
`phone` | string
`address` | string
`avatarUrl` | string
`bio` | string
`user` | [UserDTO](UserDTO.md)

## Example

```typescript
import type { UserProfileDTO } from ''

// TODO: Update the object below with actual values
const example = {
  "id": null,
  "phone": null,
  "address": null,
  "avatarUrl": null,
  "bio": null,
  "user": null,
} satisfies UserProfileDTO

console.log(example)

// Convert the instance to a JSON string
const exampleJSON: string = JSON.stringify(example)
console.log(exampleJSON)

// Parse the JSON string back to an object
const exampleParsed = JSON.parse(exampleJSON) as UserProfileDTO
console.log(exampleParsed)
```

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


