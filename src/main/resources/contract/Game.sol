pragma solidity ^0.4.11;
contract Game{
    address public owner = 0x0;
    uint256 public rewarderId = 0;
    uint public rewardNum = 0;
    uint public maxTicket = 0;
    uint public minTicket = 0;

    uint public restTicket = 0;
    uint public ticketId = 0;

    uint public pushedTicket = 0;
    //uint public gameId = 0;

    uint256 public startBlock = 0;
    uint256 public endBlock = 0;
    uint256 public gameId = 0;

    bool public over = false;
    bool public started = false;
    bool public validGame = true;



    mapping(uint=>uint256) public tickets;

    mapping(uint256 => uint256) public pushValues;


   // event JoinGameLog(address joinAddress,uint tokenNumber);
   //event RewardLog(uint256 gameId, uint256 participantId, uint rewardNum, uint256 buyTokenNum);


    uint initBlockNumber = 0;


    function Game(address inputOwnerAddress,uint256 gameIdInput, uint maxTicketInput, uint minTicketInput){
        owner = inputOwnerAddress;
        maxTicket = maxTicketInput;
        minTicket = minTicketInput;
        restTicket = maxTicket;
        gameId = gameIdInput;
    }

    function BlockNumber() constant returns (uint){
        return block.number;
    }


    function joinGame(uint256 participantId,uint256 tokenNumber) returns (bool){
       //if(msg.sender == owner) revert();
       if(started == false) revert();
       if(block.number > endBlock) revert();
       if(tokenNumber > restTicket) revert();
       for(uint i = 0; i<tokenNumber-1; i++){
           ticketId += 1;
           tickets[ticketId] = participantId;
       }
       pushValues[participantId] += tokenNumber;
       restTicket -= tokenNumber;
       return true;
    }

    function start(uint blockNumberAfter) returns(bool){
        if(msg.sender != owner) revert();
        startBlock = block.number;
        endBlock = blockNumberAfter+startBlock;
        started = true;
        return true;
    }

    function result() returns (bool){
        if(over != false) revert();
        if(block.number < endBlock+1) revert();
        if(msg.sender != owner) revert();
        if(ticketId < minTicket){
            over = true;
            started = false;
            validGame = false;
        }
        uint256 blockHash = uint256(block.blockhash(endBlock+1));
        rewardNum = blockHash%(ticketId)+1;
        rewarderId = tickets[rewardNum];
       // RewardLog(gameId,participantId,rewardId, pushValues[participantId]);
        over = true;
        started = false;
        return true;
    }


    function blockNumber() constant returns(uint) {
        return block.number;
    }
}


contract GameFactory{
    address public owner = 0x0;
    mapping(uint=>address) public gameMap;
    //mapping(uint=>uint) public gameStartBlock;
    //mapping(uint=>uint) public gameStopBlock;
    mapping(uint=>uint256) public gameRewards;

    event AddGameLog(uint gameId, address gameAddress);
    event JoinGameLog(uint gameId, uint256 participantId,uint tokenNumber);
    event StartGameLog(uint gameid);
    event RewardLog(uint gameId, uint256 participantId, uint rewardNum);

    function GameFactory(){
        owner = msg.sender;
    }

    function newGame(uint256 gameId, uint maxTicketInput, uint minTicketInput) returns (address){
        if(msg.sender != owner) revert();
        address newgameaddress = new Game(this,gameId,maxTicketInput,minTicketInput);
        gameMap[gameId] = newgameaddress;
        AddGameLog(gameId, newgameaddress);
        return newgameaddress;
    }

    function startGame(uint gameId, uint blockNumber) returns (bool){
        if(msg.sender != owner) revert();
        Game g = Game(gameMap[gameId]);
        if(g.start(blockNumber) == true){
           // gameStartBlock[gameId] = g.startBlock();
            //gameStopBlock[gameId] = g.endBlock();
            StartGameLog(gameId);
            return true;
        }
        return false;
    }

    function getResult(uint gameId)returns (bool){
        if(msg.sender != owner) revert();
        Game g = Game(gameMap[gameId]);
        if(g.result()){
            gameRewards[gameId]=g.rewarderId();
            RewardLog(gameId,g.rewarderId(),g.rewardNum());
            return true;
        }
        return false;

    }

    function joinGame(uint gameId, uint256 participantId,uint256 tokenNumber) returns (bool){
        if(msg.sender != owner) revert();
        Game g = Game(gameMap[gameId]);
        if(g.joinGame(participantId,tokenNumber)){
            JoinGameLog(gameId,participantId,tokenNumber);
            return true;
        }
        return false;
    }

    function blockNumber() constant returns(uint) {
        return block.number;
    }

    function gameTimeInfo(uint gameId) constant returns(uint,uint){
        Game g = Game(gameMap[gameId]);
        return (g.startBlock(),g.endBlock());
    }

    function ticketInfo(uint gameId,uint256 ticketId) constant returns(uint256){
        Game g = Game(gameMap[gameId]);
        return g.tickets(ticketId);

    }



}















