import http from "k6/http";
import { check } from 'k6';

export const options = {
    stages: [
    // ramp up from 0 to 20 VUs over the next 30s seconds
    { duration: '30s', target: 70 },
    // run 20 VUs over the next 30 seconds
    { duration: '30s', target: 70 },
    // ramp down from 20 to 0 VUs over the next 30 seconds
    { duration: '30s', target: 0 },
    ],
    thresholds: {
        http_req_failed: ['rate<0.01'], // http errors should be less than 1%
        http_req_duration: ['p(95)<1100'], // 95% of requests should be below 1100ms=1.1s
        "checks": ["rate>0.98"],
    },
};
const BASE_URL = __ENV.BASE_URL || "http://localhost:3333";

export default function () {
  let restrictions = {
    maxCaloriesPerSlice: 500,
    mustBeVegetarian: false,
    excludedIngredients: ["pepperoni"],
    excludedTools: ["knife"],
    maxNumberOfToppings: 6,
    minNumberOfToppings: 2,
  };
  let res = http.post(`${BASE_URL}/api/pizza`, JSON.stringify(restrictions), {
    headers: {
      "Content-Type": "application/json",
      "Authorization": "token abcdef0123456789",
      "X-User-ID": 23423,
    },
  });
  check(res, {
    'is status 200': (r) => r.status === 200,
    'body size is < 1KB': (r) => r.body.length < 1024,
  });
  console.log(`${res.json().pizza.name} (${res.json().pizza.ingredients.length} ingredients)`);
}
