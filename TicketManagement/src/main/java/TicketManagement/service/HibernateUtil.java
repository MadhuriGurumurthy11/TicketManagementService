/**
 * 
 */
package TicketManagement.service;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * @author Madhuri Gurumurthy
 * 
 *         <p>
 *         This class is used to build Session factory and return one on demand.
 * 
 *         </p>
 *
 */
@SuppressWarnings("deprecation")
public class HibernateUtil
{
	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory()
	{
		try
		{
			// Create the SessionFactory from hibernate.cfg.xml
			return new AnnotationConfiguration().configure()
					.buildSessionFactory();

		} catch (Throwable ex)
		{
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory()
	{
		return sessionFactory;
	}

	public static void shutdown()
	{
		// Close caches and connection pools
		getSessionFactory().close();
	}
}
