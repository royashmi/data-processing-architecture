﻿SELECT 	all_measures.device_pk,
	all_measures.measure_timestamp,
	all_measures.measure						AS measure,
	clustered_measures.cluster_avg_measure				AS expected_measure,
	all_measures.measure - clustered_measures.cluster_avg_measure	AS delta,
	'Delta between current and last month avg consumption '		AS measure_description,
	measure_unit,
	device_location
	
FROM	(SELECT	all_measures.device_pk,
		most_recent_measure.measure_timestamp		  AS measure_timestamp,
		all_measures.measure_timestamp		          AS cluster_timestamp,
		date_part('hour', all_measures.measure_timestamp) AS cluster_pivot_hour,
		avg(all_measures.measure) 	OVER w	  	  AS cluster_avg_measure,
		rank()				OVER w
	
	FROM 	"DBMS_EMS_Schema"."New_Q8_NormalizeConsumptionsByLocationSquareMeters"  AS all_measures
		INNER JOIN 
		"DBMS_EMS_Schema"."New_Q8_NormalizeConsumptionsByLocationSquareMeters"	AS most_recent_measure
		ON  most_recent_measure.rank 	    = 1
		AND most_recent_measure.device_pk   = all_measures.device_pk	
		AND all_measures.measure_timestamp  > most_recent_measure.measure_timestamp  - interval '1 month'

	WINDOW 	w AS (PARTITION BY all_measures.device_pk, 	
				   date_part('hour', all_measures.measure_timestamp)
		      ORDER BY all_measures.measure_timestamp DESC
	              RANGE BETWEEN CURRENT ROW AND UNBOUNDED FOLLOWING)
	              
	) AS clustered_measures
	INNER JOIN 
	"DBMS_EMS_Schema"."New_Q8_NormalizeConsumptionsByLocationSquareMeters"	AS all_measures
	ON  clustered_measures.rank = 1 
	AND all_measures.device_pk = clustered_measures.device_pk
	AND all_measures.measure_timestamp = clustered_measures.measure_timestamp	
	AND date_part('hour', all_measures.measure_timestamp) = clustered_measures.cluster_pivot_hour