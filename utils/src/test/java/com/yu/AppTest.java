package com.yu;

import com.yu.utils.IoUtils;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
        Assert.assertTrue(IoUtils.existsUrl("http://m10.music.126.net/20171216213107/9db1ae0a187764aadb4306002df6b581/ymusic/e943/aef7/4cf6/504a58643cb902e179e299ba6d77e9fe.mp3"));
    }
}
