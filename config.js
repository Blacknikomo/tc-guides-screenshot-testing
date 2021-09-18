const path = require("path");

const BASE_URL = process.env.BASE_URL || "https://jetbrains.com";
const PATHS_TO_CHECK = process.env.PATHS_TO_CHECK || "teamcity,space,webstorm";
const PATHS = {
  screenshots: path.resolve(__dirname, "screenshots")
}
const CONFIG = {
  BASE_URL,
  PATHS_TO_CHECK,
  PATHS
}

module.exports = CONFIG;