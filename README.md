# Weather Forecast
This project contains two separate module:
<ul>
    <li>forecast</li>
    <li>scheduler</li>
</ul>
Init configuration:
<ol>
    <li>create database with name <b>master</b> and make configuration which described in <i>application.properties</i>
    in <i>forecast</i> module or set a new one configuration and will write it to <i>application.properties</i>;</li>
    <li>create two database (<b>england_tenant</b> and <b>ukraine_tenant</b>) with configuration which described
    in migration <i>V2.2__insert_ukraine_tenant_and_england_tenant_data.sql</i> or will set new configuration
    and will write it to this migration;</li>
</ol>
You can manage cities for auto-fetch weather forecast by using next endpoints:
<ul>
    <li>By <b>POST</b> method <i>weather-forecast/city</i></li>
    <li>By <b>GET</b> method <i>weather-forecast/city/all</i></li>
    <li>By <b>GET</b> method <i>weather-forecast/city/all-by-country</i></li>
    <li>By <b>DELETE</b> method <i>weather-forecast/city</i></li>
</ul>
To get the weather for a specific city, you need to call the first endpoint. By third endpoint you can make backup
<ul>
    <li>By <b>GET</b> method <i>weather-forecast/city/{id}</i></li>
    <li>By <b>GET</b> method <i>weather-forecast/city/{id}/weather</i></li>
    <li>By <b>GET</b> method <i>weather-forecast/backup</i></li>
</ul>
For start application you will need start <i>ForecastApplication</i>.
Next, add city by name of city and country abbreviation.
<br/><br/>
<i>cities.json</i> in resources for help.
<br/>
<br>After that, start <i>SchedulerApplication</i>.
After added city to db, <i>Scheduler</> itself will put city id to endpoint <i>weather-forecast/city{id}</i> in <i>forecast</i>
