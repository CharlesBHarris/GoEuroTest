This Java file implements a class to solve the GoEuro Java Developer Test
found here: https://github.com/goeuro/dev-test. The algorithm queries the GoEuro 
API at http://api.goeuro.com/api/v2/position/suggest/en/CITY_NAME, where CITY_NAME 
is an appropriate command line argument. The returned JSON object is then parsed 
and a CSV file is output containing the columns _id, name, type, latitude,
and longitude.
