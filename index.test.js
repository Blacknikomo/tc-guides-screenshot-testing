const pixelMatch = require("pixelmatch");
const fsAsync = require("fs/promises");
const fs = require("fs");
const PNG = require("pngjs").PNG;

const CONFIG = require("./config");

const suiteName = 'Screenshot testing';

describe(suiteName, () => {
  const prepareFolders = async () => {
    try {
      await fsAsync.access(CONFIG.PATHS.screenshots);
    } catch (error) {
      await fsAsync.mkdir(CONFIG.PATHS.screenshots)
    }
  }

  const pagesToCheck = CONFIG.PATHS_TO_CHECK.split(",")

  for (let i = 0; i < pagesToCheck.length; i++) {
    const currentPage = pagesToCheck[i];
    const testName = `${currentPage} page screenshots stay same.`;
    it(testName, async () => {
      await prepareFolders()

      const referenceImagePath = `${CONFIG.PATHS.screenshots}/${currentPage}-reference.png`;
      const newImagePath = `${CONFIG.PATHS.screenshots}/${currentPage}-new.png`;
      const diffImagePath = `${CONFIG.PATHS.screenshots}/${currentPage}-diff.png`;

      let img1;
      let img2;

      if (fs.existsSync(referenceImagePath)) {
        img1 = PNG.sync.read(fs.readFileSync(referenceImagePath));
      } else {
        expect(img1).toBe(true);
        return;
      }

      if (fs.existsSync(referenceImagePath)) {
        img2 = PNG.sync.read(fs.readFileSync(newImagePath));
      } else {
        expect(img2).toBe(true);
        return;
      }

      const {width, height} = img1;
      const diff = new PNG({width, height});
      const numberOfNonMatchedPixels = pixelMatch(img1.data, img2.data, diff.data, width, height, {threshold: 0.1});

      if (numberOfNonMatchedPixels > 0) {
        await fsAsync.writeFile(diffImagePath, PNG.sync.write(diff));
      }


      console.log("##teamcity[testStarted name='%s']", testName);

      if (numberOfNonMatchedPixels > 0) {
        console.log("##teamcity[testFailed name='%s' message='FAILED']", testName);
        console.log(`##teamcity[testMetadata testName='${testName}' type='image' value='screenshots/${currentPage}-new.png']`)
        console.log(`##teamcity[testMetadata testName='${testName}' type='image' value='screenshots/${currentPage}-diff.png']`)
      } else {
        console.log("##teamcity[testFinished name='%s']", testName);
      }


      console.log(`##teamcity[testMetadata testName='${testName}' type='image' value='screenshots/${currentPage}-reference.png']`)
      expect(numberOfNonMatchedPixels).toBe(0)

    }, 60000)

  }
})
