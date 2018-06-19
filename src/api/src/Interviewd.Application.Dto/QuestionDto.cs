using Interviewd.Common;

namespace Interviewd.Application.Dto
{
    public class QuestionDto : IIdentifiable
    {
        public string Id { get; set; }

        public string Name { get; set; }

        public string Description { get; set; }
    }
}
