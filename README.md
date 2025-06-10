# URL Shortener Coding Task

# introduction

This is a simple application will accept a URL and shorten it.

it allows users to customise short url's and store the url and its shortened version for later re-use.

# building

see [BUILDING.md](/tpximpact-task/src/site/markdown/BUILDING.md)

# running

see [RUNNING.md](/tpximpact-task/src/site/markdown/RUNNING.md)

When I read that one of the Deliverables was a "Simple UI", I decided to create a command line interface.
This surfaced an issue
with the OpenAPI spec, which expects to be called from a web UI (as the GET returns a Redirect).

I would update the OpenAPI spec to allow for a GET to return a 200 OK with the full URL in the body, but I didn't want
to change the spec at this stage.

The reason I would change the spec is that I would not be able to guarantee that the system calling the API would be a
web client, There may be another system between the web client and the API.

-----

# Original README

## Task

Build a simple **URL shortener** in a ** preferably JVM-based language** (e.g. Java, Kotlin).

It should:

- Accept a full URL and return a shortened URL.
- Persist the shortened URLs across restarts.
- Allow a user to **customise the shortened URL** (e.g. user provides `my-custom-alias` instead of a random string).
- Expose a **simple UI** (basic form is fine — no need for a polished design).
- Expose a **RESTful API** to perform create/read/delete operations on URLs.  
  → Refer to the provided [`openapi.yaml`](./openapi.yaml) for API structure and expected behaviour.
- Include the ability to **delete a shortened URL** via the API.
- **Have tests**.
- Be containerised (e.g. Docker).
- Include instructions for running locally.

## Rules

- Fork the repository and work in your fork. Do not push directly to the main repository.
- We suggest spending no longer than **4 hours**, but you can take longer if needed.
- Commit often with meaningful messages.
- Write tests.
- Use the provided [`openapi.yaml`](./openapi.yaml) as a reference.
- Focus on clean, maintainable code.

## Deliverables

- Working code.
- Simple UI.
- RESTful API matching the OpenAPI spec.
- Tests.
- Dockerfile.
- README with:
    - How to build and run locally.
    - Example usage (UI and/or API).
    - Any notes or assumptions.
