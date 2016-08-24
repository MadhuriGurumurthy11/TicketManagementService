package TicketManagement.dao;

/**
 * 
 */

/**
 * @author Madhuri Gurumurthy
 * 
 *         <p>
 *         This is a SeatHold class which contains data about the level, row
 *         number and seat numbers of the specific seats.
 *         </p>
 *
 */
public class SeatHold
{
	private int seatHoldId;
	private int levelId;
	private String rowNumber;
	private int noOfSeats;
	private String seatNumbers;
	private String emailId;
	private String status;
	private String confirmationCode;

	/**
	 * @return the confirmationCode
	 */
	public String getConfirmationCode()
	{
		return confirmationCode;
	}

	/**
	 * @param confirmationCode
	 *            the confirmationCode to set
	 */
	public void setConfirmationCode(String confirmationCode)
	{
		this.confirmationCode = confirmationCode;
	}

	/**
	 * @return the seatHoldId
	 */
	public int getSeatHoldId()
	{
		return seatHoldId;
	}

	/**
	 * @param seatHoldId
	 *            the seatHoldId to set
	 */
	public void setSeatHoldId(int seatHoldId)
	{
		this.seatHoldId = seatHoldId;
	}

	/**
	 * @return the levelId
	 */
	public int getLevelId()
	{
		return levelId;
	}

	/**
	 * @param levelId
	 *            the levelId to set
	 */
	public void setLevelId(int levelId)
	{
		this.levelId = levelId;
	}

	/**
	 * @return the rowNumber
	 */
	public String getRowNumber()
	{
		return rowNumber;
	}

	/**
	 * @param rowNumber
	 *            the rowNumber to set
	 */
	public void setRowNumber(String rowNumber)
	{
		this.rowNumber = rowNumber;
	}

	/**
	 * @return the noOfSeats
	 */
	public int getNoOfSeats()
	{
		return noOfSeats;
	}

	/**
	 * @param noOfSeats
	 *            the noOfSeats to set
	 */
	public void setNoOfSeats(int noOfSeats)
	{
		this.noOfSeats = noOfSeats;
	}

	/**
	 * @return the seatNumbers
	 */
	public String getSeatNumbers()
	{
		return seatNumbers;
	}

	/**
	 * @param seatNumbers
	 *            the seatNumbers to set
	 */
	public void setSeatNumbers(String seatNumbers)
	{
		this.seatNumbers = seatNumbers;
	}

	/**
	 * @return the emailId
	 */
	public String getEmailId()
	{
		return emailId;
	}

	/**
	 * @param emailId
	 *            the emailId to set
	 */
	public void setEmailId(String emailId)
	{
		this.emailId = emailId;
	}

	/**
	 * @return the status
	 */
	public String getStatus()
	{
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status)
	{
		this.status = status;
	}

}
