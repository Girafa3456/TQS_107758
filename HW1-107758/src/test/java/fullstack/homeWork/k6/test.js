import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = { vus: 5, duration: '10s' };

export default function () {
  const url = 'http://localhost:8080/api/bookings/book';
  const payload = JSON.stringify({
    mealId: 1,
    studentName: 'TestUser'
  });

  const headers = { 'Content-Type': 'application/json' };

  let res = http.post(url, payload, { headers });

  http.get('http://localhost:8080/api/restaurants');
  http.get('http://localhost:8080/api/restaurants/1');

  check(res, {
    'status is 200': (r) => r.status === 200,
  });

  sleep(1);
}

