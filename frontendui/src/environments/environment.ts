// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.
function getBaseUrl(val:any){
  let baseUrl;
  switch(val){
    case 'auth':
      baseUrl = 'http://127.0.0.1:8801/zuul-auth2';
      break;
    case 'company':
      baseUrl = 'http://127.0.0.1:8801/zuul-company';
      break;
    case 'sector':
      baseUrl = 'http://127.0.0.1:8801/zuul-sector';
      break;
    case 'upload':
      baseUrl = 'http://127.0.0.1:8801/zuul-upload';
      break;
    case 'stockExchange':
      baseUrl = 'http://127.0.0.1:8801/zuul-stock-exchange';
      break;  
    case 'ipo':
      baseUrl = 'http://127.0.0.1:8801/zuul-ipo';
      break; 
    default:
      baseUrl = 'http://127.0.0.1:8801/';  
  }
  return baseUrl;
}

export const environment = {
  production: false,
  getBaseUrl
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
