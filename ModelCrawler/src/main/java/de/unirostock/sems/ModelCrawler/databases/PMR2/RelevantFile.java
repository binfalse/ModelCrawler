package de.unirostock.sems.ModelCrawler.databases.PMR2;

import java.util.Date;

import de.unirostock.sems.ModelCrawler.databases.Interface.Change;

public class RelevantFile {

	private String filePath;
	private String modelId;
	private String latestKnownVersionId = null;
	private Date latestKnownVersionDate = null;
	
	private PmrChangeSet changeSet = null;
	
	public RelevantFile( String filePath, String modelId ) {
		this.filePath = filePath;
		this.modelId = modelId;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public String getModelId() {
		return modelId;
	}
	
	/**
	 * Sets the latest known Version of this model and the changeSet of it
	 * 
	 * @param latestVersionId
	 * @param latestVersionDate
	 * @param changeSet
	 */
	public void setLatestKnownVersion( String latestVersionId, Date latestVersionDate, PmrChangeSet changeSet ) {
		this.latestKnownVersionId = latestVersionId;
		this.latestKnownVersionDate = latestVersionDate;
		this.changeSet = changeSet;
	}
	
	/**
	 * Sets the latest known Version of this model
	 * 
	 * @param latestVersionId
	 * @param latestVersionDate
	 */
	public void setLatestKnownVersion( String latestVersionId, Date latestVersionDate ) {
		setLatestKnownVersion( latestVersionId, latestVersionDate, null );
	}
	
	/**
	 * Gets the latest known VersionId. Not the real latest, but the versionId setted with setLatestKnownVersion()
	 * 
	 * @return versionId
	 */
	public String getLatestKnownVersionId() {
		return latestKnownVersionId;
	}
	
	/**
	 * Gets the latest known VersionDate. Not the real latest, but the versionDate setted with setLatestKnownVersion()
	 * 
	 * @return versionDate
	 */
	public Date getLatestKnownVersionDate() {
		return latestKnownVersionDate;
	}
	
	/**
	 * Gets the real latest versionId. Either from the latestKnownVersion() or from the changeSet
	 * 
	 * @return versionId
	 */
	public String getLatestVersionId() {
		
		if( changeSet == null ) {
			// no changeSet available
			return latestKnownVersionId;
		}
		
		Change change; 
		if( (change = changeSet.getLatestChange()) != null ) {
			// take the latest change from the changeSet
			return change.getVersionId();
		}
		else {
			// no change stored in the changeSet
			return latestKnownVersionId;
		}
		
	}
	
	/**
	 * Gets the real latest versionDate. Either from the latestKnownVersion() or from the changeSet
	 * 
	 * @return versionDate
	 */
	public Date getLatestVersionDate() {
		
		if( changeSet == null ) {
			// no changeSet available
			return latestKnownVersionDate;
		}
		
		Change change; 
		if( (change = changeSet.getLatestChange()) != null ) {
			// take the latest change from the changeSet
			return change.getVersionDate();
		}
		else {
			// no change stored in the changeSet
			return latestKnownVersionDate;
		}
	
	}
	
	/**
	 * Return the changeSet or null, if no one was setted and no change added
	 * 
	 * @return PmrChangeSet or null
	 */
	public PmrChangeSet getChangeSet() {
		return changeSet;
	}
	
	/**
	 * Adds a change to the changeSet and creates one if necessary.
	 * 
	 * @param change
	 */
	public void addChange( PmrChange change ) {
		
		// creates ChangeSet, if necessary
		if( changeSet == null ) {
			changeSet = new PmrChangeSet(modelId);
		}
		
		// adds the change
		changeSet.addChange(change);
	}

}