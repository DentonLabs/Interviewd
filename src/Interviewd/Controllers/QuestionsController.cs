using System.Collections.Generic;
using System.Threading.Tasks;
using Interviewd.Application;
using Interviewd.Application.Dto;
using Microsoft.AspNetCore.Mvc;

namespace Interviewd.Controllers
{
    [Route("questions")]
    public class QuestionsController : Controller
    {
        private readonly IQuestionManager _QuestionManager;

        public QuestionsController(IQuestionManager questionManager)
        {
            _QuestionManager = questionManager;
        }

        [HttpGet]
        public async Task<IEnumerable<QuestionDto>> GetQuestions()
        {
            return await _QuestionManager.GetQuestions();
        }

        [HttpPost]
        public async Task PostQuestion([FromBody]QuestionDto questionDto)
        {
            await _QuestionManager.CreateQuestion(questionDto);
        }
    }
}