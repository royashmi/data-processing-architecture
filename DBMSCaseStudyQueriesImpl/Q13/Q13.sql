﻿SELECT 	all_measures.device_pk,
	date_part('hour', all_measures.measure_timestamp),
	avg(all_measures.measure_avg_10min),
	sum(all_measures.measure_avg_10min),
	count(all_measures.measure_avg_10min)
	
FROM 	"DBMS_EMS_Schema"."Q7_10minAVG"   		AS all_measures
	INNER JOIN 
	(SELECT device_pk, 
		max(measure_timestamp) AS current_ts
	FROM 	"DBMS_EMS_Schema"."Q7_10minAVG"
	GROUP BY device_pk) 		   		AS most_recent_measure
	ON  most_recent_measure.device_pk = all_measures.device_pk	
	AND all_measures.measure_timestamp >=  most_recent_measure.current_ts  - interval '3 day'
	
GROUP BY all_measures.device_pk, date_part('hour', all_measures.measure_timestamp)

ORDER BY all_measures.device_pk, date_part('hour', all_measures.measure_timestamp)




/*
SELECT 	all_measures.device_pk,
	date_part('hour', all_measures.measure_timestamp),
	avg(all_measures.measure_avg_10min),
	sum(all_measures.measure_avg_10min),
	count(all_measures.measure_avg_10min)
	
FROM 	"DBMS_EMS_Schema"."Q7_10minAVG"   		AS all_measures
	INNER JOIN 
	(SELECT device_pk, 
		max(measure_timestamp) AS current_ts
	FROM 	"DBMS_EMS_Schema"."Q7_10minAVG"
	GROUP BY device_pk) 		   		AS most_recent_measure
	ON  most_recent_measure.device_pk = all_measures.device_pk	
	AND all_measures.measure_timestamp >=  most_recent_measure.current_ts  - interval '1 day'
	
GROUP BY all_measures.device_pk, date_part('hour', all_measures.measure_timestamp)

ORDER BY all_measures.device_pk, date_part('hour', all_measures.measure_timestamp)
*/