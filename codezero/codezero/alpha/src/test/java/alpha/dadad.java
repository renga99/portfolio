//package alpha;
//
//public class dadad {
//
//	@Test
//	public void downloadAFile() throws Exception {
//	    FileDownloader downloadTestFile = new FileDownloader(driver);
//	    driver.get("http://www.localhost.com/downloadTest.html");
//	    WebElement downloadLink = driver.findElement(By.id("fileToDownload"));
//	    String downloadedFileAbsoluteLocation = downloadTestFile.downloadFile(downloadLink);
//
//	    assertThat(new File(downloadedFileAbsoluteLocation).exists(), is(equalTo(true)));
//	    assertThat(downloadTestFile.getHTTPStatusOfLastDownloadAttempt(), is(equalTo(200)));
//	}
//
//	@Test
//	public void downloadAnImage() throws Exception {
//	    FileDownloader downloadTestFile = new FileDownloader(driver);
//	    driver.get("http://www.localhost.com//downloadTest.html");
//	    WebElement image = driver.findElement(By.id("ebselenImage"));
//	    String downloadedImageAbsoluteLocation = downloadTestFile.downloadImage(image);
//
//	    assertThat(new File(downloadedImageAbsoluteLocation).exists(), is(equalTo(true)));
//	    assertThat(downloadTestFile.getHTTPStatusOfLastDownloadAttempt(), is(equalTo(200)));
//	}
//}
