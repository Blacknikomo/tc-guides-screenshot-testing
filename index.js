const puppeteer = require("puppeteer");
const CONFIG = require("./config");
const fsAsync = require("fs/promises");

const prepareFolders = async () => {
  try {
    const folderExists = await fsAsync.access(CONFIG.PATHS.screenshots);
  } catch (error) {
    await fsAsync.mkdir(CONFIG.PATHS.screenshots)
  }
}

(async () => {
  await prepareFolders();
  const browser = await puppeteer.launch();
  const page = await browser.newPage();

  const pagesToCheck = CONFIG.PATHS_TO_CHECK.split(",")
  for (let i = 0; i < pagesToCheck.length; i++) {
    const pageTocheck = pagesToCheck[i]
    await page.setViewport({width: 1920, height: 1080});
    await page.goto(`${CONFIG.BASE_URL}/${pageTocheck}`, { waitUntil: "networkidle2"});
    
    await page.waitForTimeout(2000);
    await page.screenshot({ path: `${CONFIG.PATHS.screenshots}/${pageTocheck}-new.png`, clip: {
      width: 1920,
      height: 1400,
      x: 0,
      y: 0,
    }});
  }

  await browser.close();
})();
