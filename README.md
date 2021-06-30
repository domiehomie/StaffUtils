# StaffUtils

## Trust score

When a player joins, they have 75% trust score. They can get the last 25% by playing. (1% each day) When they get a punishment, their trust score will decrease. When a player's trust score hits 0, they will receive a permanent ban. 

| Punishment | Score removed |
| :--------- | ------------- |
| Ban        | 15%           |
| Mute       | 7%            |
| Kick       | 7%            |
| Warn       | 3%            |

## Commands

`/suplayer <player>` - Views all information on a player. (If they are muted/banned, their trust score, how many warnings, notes and reports they have.)

`/sunote <player> <note>` - Adds a note to a player.

`/suwarn <player> <reason>` - Warns a player.

`/sukick <player> <reason>` - Kicks a player.

`/sumute <player> <time> <reason>` - Mutes a player. (Time formatted: "\<number>m/s/d")

`/removepunishment <BAN|MUTE> <player>` - Removes the current ban/mute on a player.

`/suban <player> <time> <reason>` - Mutes a player. (Time formatted: "\<number>m/s/d") To permban, put `perm` instead of the time.

------

`/createticket <type> <message>` - Creates a ticket. Ticket types can be customized in config.

`/setticketstatus <ticket> <status>` - Updates the status of a ticket. Statuses can be `OPEN`, `IN_PROGRESS` and `CLOSED`.

`/viewtickets [status/all]` - Views all open tickets, or all tickets with the status provided.

`/openticket <ticket>` - Lets you see all messages in your chat.

`/respondinticket <ticket> <message>` - Lets you respond in a ticket.

------

`/sureport <player> <reason>` - Reports a player. Reasons can be specified in config.

`/sugetreports` - Lets you view all unhandled reports.

`/suclosereport` - Closes a report.

## Permissions

`staffutils.addnote` - To add notes
`staffutils.addwarning` - To add warnings
`staffutils.getplayer` - To get the information for a player.
`staffutils.kick` - To kick players.
`staffutils.tickets.create` - To create tickets
`staffutils.tickets.viewall` - To view all tickets
`staffutils.tickets.setstatus` - To set the status of a ticket
`staffutils.tickets.open` - To open a ticket
`staffutils.tickets.respond` - To respond in a ticket.
`staffutils.mute` - To mute players.
`staffutils.removepunishment` - To remove punishments on players.
`staffutils.ban` - To ban players.
`staffutils.reports.open` - To open a report.
`staffutils.reports.getall` - To view all reports.
`staffutils.reports.close` - To close reports.
`staffutils.log` - To receive logs.

## Logging

You can log everything to a file. To enable this, just add a `logs.txt` file in the plugin directory. 
You can also have logs sent to staff. To do this, give your staff the `staffutils.log` permission.
