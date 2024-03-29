const fs = require('fs')
const formatter = require('./gatling-stats-formatter')

const getDirectories = source =>
    fs.readdirSync(source, { withFileTypes: true })
        .filter(dirent => dirent.isDirectory())
        .map(dirent => dirent.name)

const createSummary = async (core, reportsDirectory) => {
    const lastRuns = getDirectories(reportsDirectory)

    for (const run of lastRuns) {
        const stats = JSON.parse(fs.readFileSync(`${reportsDirectory}/${run}/js/stats.json`).toString());
        let tableContent = [
            [
                { data: 'Request', header: true },
                { data: 'Total', header: true },
                { data: 'OK', header: true },
                { data: 'KO', header: true },
                { data: '% KO', header: true },
                { data: 'Cnt/s', header: true },
                { data: 'Min', header: true },
                { data: '50th pct', header: true },
                { data: '75th pct', header: true },
                { data: '95th pct', header: true },
                { data: '99th pct', header: true },
                { data: 'Max', header: true },
                { data: 'Mean', header: true },
                { data: 'Std', header: true },
            ]
        ];

        const formattedStats = formatter.formatStats(stats);
        for (const line of formattedStats) {
            tableContent.push(formatLine(line, 1));
            if (line.subContent !== 0) {
                for (const subContent of line.subContent) {
                    tableContent.push(formatLine(subContent, 2));
                }
            }
        }

        console.log(process.env)
        const GITHUB_REPOSITORY_PARTS = process.env.GITHUB_REPOSITORY.split("/");
        const GITHUB_USER = GITHUB_REPOSITORY_PARTS[0]
        const GITHUB_REPOSITORY_NAME = GITHUB_REPOSITORY_PARTS[1]
        const reportUrl = `https://${GITHUB_USER}.github.io/${GITHUB_REPOSITORY_NAME}/gatling/${run}/`

        await core.summary
            .addHeading(`Results for ${run}`)
            .addQuote(`<a href="${reportUrl}" target="_blank" rel="noopener noreferrer">Full Report</a>`)
            .addTable(tableContent)
            .addQuote('All times are in millisecond (ms). RPS means "Requests per Second"')
            .write()
    }
}

const formatLine = (line, level) => {
    return [
        level === 1 ? `<strong>${line.name}</strong>` : `&emsp;${line.name}`,
        line.numberOfRequests.total.toString(),
        line.numberOfRequests.ok.toString(),
        line.numberOfRequests.ko.toString(),
        line.koPercent,
        line.meanNumberOfRequestsPerSecond.toFixed(2),
        line.minResponseTime.toString(),
        line.percentiles1.toString(),
        line.percentiles2.toString(),
        line.percentiles3.toString(),
        line.percentiles4.toString(),
        line.maxResponseTime.toString(),
        line.meanResponseTime.toString(),
        line.standardDeviation.toString(),
    ]
}

module.exports = createSummary