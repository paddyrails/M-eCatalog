Use the sample-swagger.yml in the same folder as this instructions file and create a new swagger contract in yaml folder in parent folder. Following are the required apis -

I will be using this swagger file to generate Server and client stubs.

I need the server stub package name to be com.pp.cs.sales.catalog. When generating, pass the config file in the yaml folder so the package is used:
- OpenAPI Generator: use -c yaml/openapi-generator-config.yaml
- Swagger Codegen: use -c yaml/config.json

use /api/sales/catalog/v1/ as base url

use tag catalog

GET
/products?country=
Allowed country codes are us, can, mex
Fields are 
Name, Code, Description, Price