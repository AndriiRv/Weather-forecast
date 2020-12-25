# Weather Forecast
This project contains two separate module:
<ul>
    <li>forecast</li>
    <li>scheduler</li>
</ul>
Init configuration:
<ol>
    <li>Create database with name <b>master</b> and make configuration which described in <i>application.properties</i>
    in <i>forecast</i> module or set a new one configuration and will write it to <i>application.properties</i>.</li>
    <li>Create two database (<b>england_tenant</b> and <b>ukraine_tenant</b>) with configuration which described
    in migration <i>V2.2__insert_ukraine_tenant_and_england_tenant_data.sql</i> or will set new configuration
    and will write it to this migration.</li>
</ol>
<b>Before use application</b>, you will need add cities for auto-fetch weather forecast.
For this, start <i>ForecastApplication</i> and add city by name of city and country abbreviation using the following endpoints:
<ul>
    <li>By <b>POST</b> method <i>weather-forecast/city</i><b> - for add new city</b>.</li>
    <li>By <b>GET</b> method <i>weather-forecast/city/all</i><b> - for get all cities</b>.</li>
    <li>By <b>GET</b> method <i>weather-forecast/city/all-by-country</i><b> - for get cities by specific country</b>.</li>
    <li>By <b>DELETE</b> method <i>weather-forecast/city</i><b> - for delete city for specific country</b>.</li>
</ul>
Directly, work with forecast service (below endpoints can called via external service, or <i>scheduler</i> itself will call them by over time):
<ul>
    <li>By <b>GET</b> method <i>weather-forecast/city/{id}</i><b> - for fetch weather forecast by city</b>.</li>
    <li>By <b>GET</b> method <i>weather-forecast/city/{id}/weather</i><b> - for fetch weather forecast by city with date period</b>.</li>
    <li>By <b>GET</b> method <i>weather-forecast/backup</i><b> - for execute backup of database in migration files</b>.</li>
</ul>

Correct cities names can see in <i>cities.json</i> in resources in <i>forecast</i> module.
<br/>
<br>After that, start <i>SchedulerApplication</i>.
<br/>After added cities to db, <i>scheduler</i> itself will get all cities id and execute <i>weather-forecast/city{id}</i> endpoint by each of city.
<br/>If you're didn't added cities to database by <i>weather-forecast/city</i> endpoint, <i>scheduler</i> will not fetch weather forecast.