package msc_thesis.diogo_anjos;

import msc_thesis.diogo_anjos.DBMS_Version.DBMS_VersionImpl;
import msc_thesis.diogo_anjos.DBMS_Version.QueryEvaluationReport;
import msc_thesis.diogo_anjos.simulator.EnergyMeter;
import msc_thesis.diogo_anjos.simulator.Simulator;
import msc_thesis.diogo_anjos.simulator.SimulatorClient;
import msc_thesis.diogo_anjos.simulator.impl.SimulatorImpl;

/*
 * @author Diogo Anjos (diogo.silva.anjos@tecnico.ulisboa.pt)
 * 
 */

public class AppMainController {

	
	public static void main(String args[]) throws Exception{
		
		// Prepare Database  ====================================================
		DBMS_VersionImpl dbms_versionImpl = new DBMS_VersionImpl(); 
		dbms_versionImpl.truncateAll_DatapointReadingTable();
		dbms_versionImpl.insertInto_DatapointReadingTable_BatchMode("2014-03-17 10:00:00", "2014-03-17 12:00:05", EnergyMeter.LIBRARY); // 2h
//		dbms_versionImpl.insertInto_DatapointReadingTable_BatchMode("2014-03-17 10:00:00", "2014-03-19 10:00:06", EnergyMeter.LIBRARY); // 48h
		
		//  Prepare Simulator  ====================================================
		Simulator simulatorLibrary = new SimulatorImpl(EnergyMeter.LIBRARY, "2014-03-17  12:01:05", "2014-03-17  12:10:05");	//2h
//		Simulator simulatorLibrary = new SimulatorImpl(EnergyMeter.LIBRARY, "2014-03-19 10:01:00", "2014-03-19 10:10:05");	//48h
		
		simulatorLibrary.setSpeedTimeFactor(2);
		System.out.println(simulatorLibrary); 
		simulatorLibrary.registerNewClient(dbms_versionImpl);
		
		// Init Simulation  ====================================================
		simulatorLibrary.start();
		
	}
	
	
}
