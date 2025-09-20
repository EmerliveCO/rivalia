# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased] - 2025-05-09

### Added

- v0.1.0 Create new Dynamic Web Client 
- v0.2.0 GlobalExceptionHandler
- v0.3.0 Create User Endpoint
- v0.4.0 Edit feature
- v0.5.0 Verify authentication token with auth api
- v0.6.0 Unique index for idAuth

### Changed

- Implements a function what accepts dynamic parameters for building HTTP requests with WebClient.
- Lombok is added and remove manual constructors
- GlobalExceptionHandler to manage all exceptions
- Create a user endpoint that consumes the Auth API to create a user, with a fallback method that deletes the user in the Auth API if something happens in the database
- New edit user feature, the endpoint search first if the user exists with idAuth 
- New functionability that verify the bearer token with Auth Api User Endpoint
- New index for idAuth field in users table

### Removed